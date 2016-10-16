package trellogitintegration.exec;

/**
 * This class stores the result from the command execution
 * Created: Oct 16, 2016
 * @author Man Chon Kuok
 */
public class CmdExecutionResult {

  private String stdOutput;
  private String errOutput;
  private Throwable exception;

  /**
   * Creates an instance of the CmdExecutionResult
   * @param stdOutput Output from Standard out, should not be null. 
   * @param errOutput Output from Error stream, should not be null
   * @param exception Exception thrown during command execution, null if none 
   */
  public CmdExecutionResult(String stdOutput, String errOutput,
      Throwable exception) {
    if (stdOutput == null || errOutput == null) {
      throw new IllegalArgumentException("Output cannot be null");
    }
    
    this.stdOutput = stdOutput;
    this.errOutput = errOutput;
    this.exception = exception;
  }

  public String getStdOutput() {
    return this.stdOutput;
  }

  public String getErrOutput() {
    return this.errOutput;
  }

  public Throwable getException() {
    return this.exception;
  }

  @Override
  public String toString() {
    return "CmdExecutionResult [stdOutput=" + this.stdOutput + ", errOutput="
        + this.errOutput + "]";
  }

}
