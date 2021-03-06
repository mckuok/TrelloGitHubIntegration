package trellogitintegration.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that provides functions to sequentially or concurrently
 * executes a terminal command in the specified working directory. Created: Oct
 * 15, 2016
 * 
 * @author Man Chon Kuok
 */
public class CmdExecutor {

  /**
   * Sequentially execute the provided command. This is a blocking method call,
   * be aware of executing commands that can potentially take long to finish
   * executing. Result of the command is stored in CmdExecutionResult object,
   * where the outputs from standard out and error stream are recorded, as well
   * as possible exceptions.
   * 
   * TODO Add keyboard input functionality
   * 
   * @param command
   *          Command line command, cannot be null or empty
   * @param workingDir
   *          Directory where the command should be executed at
   * @return result of the command
   */
  public static CmdExecutionResult sequentialExecute(String command,
      File workingDir) {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Command cannot be null or empty");
    }
    if (workingDir == null || !workingDir.isDirectory()) {
      throw new IllegalArgumentException(
          "Working directory cannot be null and must" + " be a directory.");
    }
    String[] parsedCommand = parseCommand(command);
    ProcessBuilder processBuilder = new ProcessBuilder(parsedCommand);
    processBuilder.directory(workingDir);
    processBuilder.redirectErrorStream(true);
    String output = "";
    Exception exception = null;
    Process process;
    try {
      process = processBuilder.start();

      ReadStreamRunnable stdRunnable = new ReadStreamRunnable(
          process.getInputStream());
      Thread stdReadingThread = new Thread(stdRunnable);
      stdReadingThread.start();

      process.waitFor();
      stdReadingThread.join();

      output = stdRunnable.getOutput();
    } catch (IOException e) {
      if (e.getMessage().contains(CommandUnrecognizedException.INVALID_PROGRAM_TEMPLATE)) {
        exception = new CommandUnrecognizedException(e.getMessage());
      } else {
        exception = e;
      }
    } catch (InterruptedException e) {
      exception = e;
    }

    CmdExecutionResult result = new CmdExecutionResult(output, exception);

    return result;
  }

  /**
   * Parse command so that " git commit -m "test message" " becomes {"git",
   * "commit", "-m" "\"test message\""}
   * 
   * @param command command to be parsed
   * @return the String array with the command parsed into different parts
   */
  private static String[] parseCommand(String command) {
    String regex = "\"([^\"]*)\"|(\\S+)";
    List<String> commandList = new LinkedList<>();

    Matcher matcher = Pattern.compile(regex).matcher(command);
    while (matcher.find()) {
      if (matcher.group(1) != null) {
        commandList.add(matcher.group(1));
      } else {
        commandList.add(matcher.group(2));
      }
    }
    return commandList.toArray(new String[commandList.size()]);
  }

  /**
   * Runnable class to read and record input stream concurrently Created: Oct
   * 15, 2016
   * 
   * @author Man Chon Kuok
   */
  private static class ReadStreamRunnable implements Runnable {

    private final InputStream stream;
    private String output;

    public ReadStreamRunnable(InputStream stream) {
      if (stream == null) {
        throw new IllegalArgumentException("Stream cannot be null");
      }
      this.stream = stream;
    }

    @Override
    public void run() {
      try {
        this.output = this.readStream(stream);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    public String getOutput() {
      return output;
    }

    /**
     * Read and append the output from the stream
     * 
     * @param stream
     *          Stream to be read from
     * @return Output from the stream
     * @throws IOException
     */
    private String readStream(InputStream stream) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

      String line = "";
      StringBuffer output = new StringBuffer();
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

      stream.close();
      reader.close();
      return output.toString();
    }

  }

}
