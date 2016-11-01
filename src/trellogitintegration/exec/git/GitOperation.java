package trellogitintegration.exec.git;

public enum GitOperation {

  INIT("git init", "Initialize a git repo"),
  PUSH("git push origin %s", "Push to remote repo"),
  PULL("git pull origin %s", "Pull from remote repo"),
  ADD("git add %s", "Add files for commit"),
  ADD_ALL("git add .", "Add all files for commit"),
  COMMIT("git commit -m \"%s\"", "Commit changes"),
  NEW_BRANCH("git checkout -b %s", "Create new branch"),
  CHECKOUT_BRANCH("git checkout %s", "Checkout branch"),
  VERSION("git --version", "Get version"),
  STATUS("git status", "Get status"),
  LOG("git log", "Get log"),
  CLONE("git clone %s", "Clone from remote repo"),
  BRANCH("git branch", "List branches");
  
  private String command;
  private String displayable; 
  
  GitOperation(String command, String displayable) {
    this.command = command;
    this.displayable = displayable;
  }
  
  public String getCommand() {
    return this.command;
  }
  
  public String getDisplayable() {
    return this.displayable;
  }
}
