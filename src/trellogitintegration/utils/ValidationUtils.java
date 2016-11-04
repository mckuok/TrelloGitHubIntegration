package trellogitintegration.utils;

/**
 * This class contains library functions usefully for checking if arguments are null.
 * When writing methods, even for private methods, checkNull should be used to
 * make it clear that the argument cannot be null.
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class ValidationUtils {
  
  /**
   * Throws IllegalArgumentException if one of the objects is null
   * @param objects objects to be checked
   */
  public static void checkNull(Object... objects) {
    for (int i = 0; i < objects.length; i++) {
      if (objects[i] == null) {
        throw new IllegalArgumentException("Argument cannot be null"); 
      }
    }
  }
  
  /**
   * Throws IllegalArgumentException if the string is either null or empty
   * @param strings strings to be checked
   */
  public static void checkNullOrEmpty(String...strings) {
    for (int i = 0; i < strings.length; i++) {
      if (strings[i] == null || strings[i].isEmpty()) {
        throw new IllegalArgumentException("String cannot be null or empty"); 
      }
    }
  }
  
  /**
   * Throw IllegalArgumentException if the number is negative
   * @param n numbers to be checked
   */
  public static void checkNegative(Number... n) {
    for (int i = 0; i < n.length; i++) {
      if (n[i].doubleValue() < 0) {
        throw new IllegalArgumentException("Number cannot be negative");
      }
    }
  }

  
  
}
