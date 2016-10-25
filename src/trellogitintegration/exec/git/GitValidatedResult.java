package trellogitintegration.exec.git;

import trellogitintegration.exec.OperationResult;

public class GitValidatedResult extends OperationResult {
  
  private final String message;
  
  public GitValidatedResult(GitOperation operation, String message) {
    super(GitOperationValidator.validateOperation(operation, message));
    this.message = message;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

}
