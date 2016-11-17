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
    String output = this.getOutput();
    if (output.contains("could not read Username")) {
      output = "Failed to authenticate, please add the SSH for GitHub to this computer."
          + "\nVisit https://help.github.com/articles/generating-an-ssh-key/ for more details.";
    }
    
    return output;
  }

}
