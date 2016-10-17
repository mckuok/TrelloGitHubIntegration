package trellogitintegration.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class that provides functions to sequentially or concurrently executes
 * a terminal command in the specified working directory.
 * Created: Oct 15, 2016
 * @author Man Chon Kuok
 */
public class CmdExecutor {
  
  private static final String INVALID_PROGRAM_TEMPLATE = "Cannot run program";

  /**
   * Concurrently executes a terminal command in a different thread, non blocking.
   * This method is meant to be left alone when it is performing execution (for
   * example a downloading a large file using curl). However, by providing a callback
   * argument, one can examine the result of the execution and perform sequential
   * operations thereafter.
   * 
   * @param command Command line command, cannot be null or empty
   * @param workingDir Directory where the command should be executed at 
   * @param callback asynchronous callback method after execution, can be null 
   */
  public static void concurrentExecute(String command, File workingDir,
      CmdCallback callback) {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Command cannot be null or empty");
    }
    if (workingDir == null || !workingDir.isDirectory()) {
      throw new IllegalArgumentException("Working directory cannot be null and must"
          + "be a directory.");
    }
    
    Thread thread = new Thread(new Runnable() {

      @Override
      public void run() {
        CmdExecutionResult result = null;
        result = sequentialExecute(command, workingDir);
        if (callback != null) {
          callback.callback(result);
        }
      }

    });

    thread.start();
  }

  /**
   * Sequentially execute the provided command. This is a blocking method call,
   * be aware of executing commands that can potentially take long to finish
   * executing.
   * Result of the command is stored in CmdExecutionResult object, where the 
   * outputs from standard out and error stream are recorded, as well as possible
   * exceptions.
   * 
   * TODO Add keyboard input functionality
   * 
   * @param command Command line command, cannot be null or empty
   * @param workingDir Directory where the command should be executed at 
   * @return result of the command
   */
  public static CmdExecutionResult sequentialExecute(String command,
      File workingDir) {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Command cannot be null or empty");
    }
    if (workingDir == null || !workingDir.isDirectory()) {
      throw new IllegalArgumentException("Working directory cannot be null and must"
          + "be a directory.");
    }
    
    Process process = null;
    String stdOutput = "";
    String errOutput = "";
    Exception exception = null;
    try {
      process = Runtime.getRuntime().exec(command, null, workingDir);
      
      ReadStreamRunnable stdRunnable = new ReadStreamRunnable(
          process.getInputStream());
      ReadStreamRunnable errRunnable = new ReadStreamRunnable(
          process.getErrorStream());
      
      Thread stdReadingThread = new Thread(stdRunnable);
      Thread errReadingThread = new Thread(errRunnable);

      stdReadingThread.start();
      errReadingThread.start();

      process.waitFor();
      
      stdReadingThread.join();
      errReadingThread.join();

      stdOutput = stdRunnable.getOutput();
      errOutput = errRunnable.getOutput();

    } catch (IOException e) {
      if (e.getMessage().contains(INVALID_PROGRAM_TEMPLATE)) {
        exception = new CommandUnrecognizedException(e.getMessage());
      } else {
        exception = e;
      }
    } catch (InterruptedException e) {
      exception = e;
    }

    CmdExecutionResult result = new CmdExecutionResult(stdOutput, errOutput,
        exception);

    return result;
  }

  /**
   * Runnable class to read and  record input stream concurrently
   * Created: Oct 15, 2016
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
     * @param stream Stream to be read from
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
