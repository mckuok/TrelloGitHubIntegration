package trellogitintegration.exec.git;

public enum GitOperation {

  INIT("git init"),
  PUSH("git push origin %s"),
  PULL("git pull origin %s"),
  ADD("git add %s"),
  ADD_ALL("git add ."),
  COMMIT("git commit -m \"%s\""),
  NEW_BRANCH("git checkout -b %s"),
  CHECKOUT_BRANCH("git checkout %s"),
  VERSION("git --version"),
  STATUS("git status"),
  LOG("git log"),
  CLONE("git clone %s"),
  BRANCH("git branch");
  
  private String command;
  
  GitOperation(String command) {
    this.command = command;
  }
  
  public String getCommand() {
    return this.command;
  }

}
