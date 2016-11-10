package trellogitintegration.persist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import trellogitintegration.utils.ValidationUtils;

/**
 * Utility class for IO related operations
 * Created: Oct 22, 2016
 * @author Man Chon Kuok
 */
public class IOUtils {

  /**
   * Delete a folder recursively
   * 
   * @param folder
   *          the folder to be deleted
   * @return true if deleted successfully, false otherwise
   */
  public static boolean deleteFolder(File folder) {
    ValidationUtils.checkNull(folder);
    
    File[] files = folder.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.isDirectory()) {
          deleteFolder(f);
        } else {
          f.delete();
        }
      }
    }
    return folder.delete();
  }

  /**
   * Compare the two lists of files and return the newly generated ones.
   * Non-destructive
   * 
   * @param before
   * @param after
   * @return (after - before)
   */
  public static List<String> getGeneratedFiles(List<String> before,
      List<String> after) {
    ValidationUtils.checkNull(before, after);
    
    List<String> difference = new ArrayList<>(after);
    before.stream().forEach(file -> difference.remove(file));
    return difference;
  }

  /**
   * Write content to stream
   * 
   * @param outputStream
   *          stream to write to
   * @param content
   *          content to write to stream
   * @throws IOException
   */
  public static void writeToStream(OutputStream outputStream, String content)
      throws IOException {
    ValidationUtils.checkNull(outputStream, content);
    
    BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
    writer.write(content);
    writer.flush();
    writer.close();
  }

  /**
   * Read content from stream
   * 
   * @param inputStream
   *          stream to read from
   * @return content of the stream
   * @throws IOException
   */
  public static String readFromStream(InputStream inputStream)
      throws IOException {
    ValidationUtils.checkNull(inputStream);
    
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(inputStream, Charset.forName("UTF-8")));
    String line;
    StringBuilder result = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      result.append(line + "\n");
    }
    reader.close();
    return result.toString();
  }

}
