package trellogitintegration.persist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  /**
   * Delete a folder recursively
   * @param folder the folder to be deleted 
   * @return true if deleted successfully, false otherwise
   */
  public static boolean deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if(files != null) { 
        for(File f: files) {
            if(f.isDirectory()) {
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
   * @param before 
   * @param after
   * @return (after - before)
   */
  public static List<String> getGeneratedFiles(List<String> before,
      List<String> after) {
    List<String> difference = new ArrayList<>(after);
    before.stream().forEach(file -> difference.remove(file));
    return difference;
  }

}
