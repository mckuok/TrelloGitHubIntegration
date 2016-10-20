package trellogitintegration.exec.git;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trellogitintegration.exec.CmdExecutionResult;
import trellogitintegration.exec.CmdExecutor;
import trellogitintegration.exec.CommandUnrecognizedException;
import trellogitintegration.exec.git.GitConfigException.GitExceptionType;
import trellogitintegration.persist.FileUtils;

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
        throw new GitConfigException(GitExceptionType.NOT_INSTALLED);
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
    if (this.runCommand(GitOperation.CHECKOUT_BRANCH, branchName)) {
      return true;
    } else {
      return this.runCommand(GitOperation.NEW_BRANCH, branchName) &&
          this.runCommand(GitOperation.PULL, branchName);
    }
  }

  public boolean status() throws Exception {
    return this.runCommand(GitOperation.STATUS);
  }

  public String log() throws Exception {
    CmdExecutionResult result = CmdExecutor
        .sequentialExecute(GitOperation.LOG.getCommand(), this.workingDir);

    if (result.getException() == null) {
      return result.getOutput();
    } else {
      throw result.getException();
    }
  }

  public boolean clone(String target) throws Exception {
    List<String> before = Arrays.asList(this.workingDir.list());
    boolean success = this.runCommand(GitOperation.CLONE, target);

    if (success) {
      List<String> after = Arrays.asList(this.workingDir.list());
      // after.removeAll(before); unsupported
      List<String> newFiles = FileUtils.getGeneratedFiles(before, after);
      if (newFiles.isEmpty() || newFiles.size() != 1) {
        success = false;
      } else {
        this.workingDir = new File(this.workingDir, newFiles.get(0));
        success = this.workingDir.exists();
      }
    }
    return success;
  }

  public File getWorkingDirectory() {
    return this.workingDir;
  }

  private boolean runCommand(GitOperation operation) throws Exception {
    return this.runCommand(operation, "");
  }

  private boolean runCommand(GitOperation operation, String argument)
      throws Exception {
    CmdExecutionResult result = CmdExecutor.sequentialExecute(
        String.format(operation.getCommand(), argument), this.workingDir);

    if (result.getException() == null) {
      return GitOperationValidator.validateOperation(operation,
          result.getOutput());
    } else {
      throw result.getException();
    }
  }

  
}
