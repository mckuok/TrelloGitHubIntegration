package trellogitintegration.exec.git;

import java.io.File;

import trellogitintegration.exec.CmdExecutionResult;
import trellogitintegration.exec.CmdExecutor;
import trellogitintegration.exec.CommandUnrecognizedException;
import trellogitintegration.exec.git.GitConfigdException.GitExceptionType;

public class GitManager {
  
  private File workingDir;
  
  public GitManager(File workingDir) throws Exception {
    this.workingDir = workingDir;
    gitInstalledOrThrowException();
    this.workingDir = workingDir;
  }

  private void gitInstalledOrThrowException() throws Exception {
    try {
      runCommand(GitOperation.VERSION);
    } catch (Exception e) {
      if (e instanceof CommandUnrecognizedException) {
        throw new GitConfigdException(GitExceptionType.NOT_INSTALLED);
      } else {
        throw e;
      }
    }    
  }
  
  public boolean init() throws Exception {
    return this.runCommand(GitOperation.INIT);
  }
  
  public boolean add(String file) throws Exception {
    return this.runCommand(GitOperation.ADD, file);
  }
  
  public boolean addAll() throws Exception {
    return this.add(".");
  }
  
  public boolean commit(String message) throws Exception {
    return this.runCommand(GitOperation.COMMIT, message);
  }
  
  public boolean push(String branch) throws Exception {
    return this.runCommand(GitOperation.PUSH, branch);
  }
  
  public boolean pull(String branch) throws Exception {
    return this.runCommand(GitOperation.PULL, branch);
  }
  
  public boolean newBranch(String branchName) throws Exception {
    return this.runCommand(GitOperation.NEW_BRANCH, branchName);
  }
  
  public boolean checkOutBranch(String branchName) throws Exception {
    return this.runCommand(GitOperation.CHECKOUT_BRANCH, branchName);
  }
  
  public boolean status() throws Exception {
    return this.runCommand(GitOperation.STATUS);
  }
  
  public boolean log() throws Exception {
    return this.runCommand(GitOperation.LOG);
  }
  
  private boolean runCommand(GitOperation operation) throws Exception {
    return this.runCommand(operation, "");
  }
  
  private boolean runCommand(GitOperation operation, String argument) throws Exception {
    CmdExecutionResult result = CmdExecutor.sequentialExecute(String.format(operation.getCommand(), argument), this.workingDir);
    String output = "";
    if (result.getException() == null) {
      if (!result.getStdOutput().replace("\n", "").isEmpty()) {
        output = result.getStdOutput();
      } else {
        output = result.getErrOutput();
      }
      return GitOperationValidator.validateOperation(operation, output);
    } else {
      throw result.getException();
    }
  }
  
}
