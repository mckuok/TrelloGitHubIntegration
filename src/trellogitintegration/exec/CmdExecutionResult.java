package trellogitintegration.exec;

/**
 * This class stores the result from the command execution
 * Created: Oct 16, 2016
 * @author Man Chon Kuok
 */
public class CmdExecutionResult extends OperationResult<String> {

  private String output;
  private Exception exception;

  /**
   * Creates an instance of the CmdExecutionResult
   * @param output Output from command, should not be null. 
   * @param exception Exception thrown during command execution, null if none 
   */
  public CmdExecutionResult(String output, Exception exception) {
    super(determineSuccess(exception));
    
    if (output == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }
    
    this.output = output;
    this.exception = exception;
  }
  
  public String getOutput() {
    return this.output;
  }

  public Exception getException() {
    return this.exception;
  }

  @Override
  public String toString() {
    return "CmdExecutionResult [stdOutput=" + this.output + "]";
  }
  
  private static boolean determineSuccess(Exception e) {
    return e != null;
  }

  /**
   * {@inheritDoc} 
   */
  @Override
  public String getDisplayableMessage() {
    return this.getOutput();
  }

}
