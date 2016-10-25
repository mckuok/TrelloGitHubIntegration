package trellogitintegration.exec.git.github.pullrequest;

import trellogitintegration.exec.OperationResult;

public abstract class PullRequestResult extends OperationResult {

  public PullRequestResult(boolean result) {
    super(result);
  }

  public abstract String getMessage();
  
}
