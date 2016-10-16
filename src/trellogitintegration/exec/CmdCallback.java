package trellogitintegration.exec;

import trellogitintegration.exec.CmdExecutionResult;

/**
 * This class provides method to capture command line result and act on the result 
 * 
 * TODO Generalize this class
 * Created: Oct 16, 2016
 * @author Man Chon Kuok
 */
public interface CmdCallback {
  
  /**
   * Implement this method to perform asynchronous callback to concurrent execution
   * after the execution is done. 
   * 
   * @param result Result recorded from command execution. Note that if command
   * is not recognized, CmdExecutionResult.getException() should be instanceof
   * {@link CommandUnrecognizedException }
   */
  public void callback(CmdExecutionResult result);
  
}
