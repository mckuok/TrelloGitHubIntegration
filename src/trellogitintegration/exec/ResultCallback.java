package trellogitintegration.exec;

/**
 * This class provides method to capture command line result and act on the result 
 * 
 * TODO Generalize this class
 * Created: Oct 16, 2016
 * @author Man Chon Kuok
 */
public interface ResultCallback {
  
  /**
   * Implement this method to perform asynchronous callback to concurrent execution
   * after the execution is done. 
   * 
   * @param result Result from the operation before
   */
  public void callback(String result);
  
}
