package trellogitintegration.exec.git;

import trellogitintegration.exec.OperationResult;

public class GitValidatedResult extends OperationResult<String> {
  
  private final String message;
  
  public GitValidatedResult(GitOperation operation, String message) {
    super(GitOperationValidator.validateOperation(operation, message));
    this.message = message;
  }

  @Override
  public String getOutput() {
    return this.message;
  }

  /**
   * {@inheritDoc} 
   */
  @Override
  public String getDisplayableMessage() {
    return this.getOutput();
  }

}
