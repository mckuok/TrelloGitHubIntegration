package trellogitintegration.exec.git;

public class GitConfigException extends Exception {

  private static final long serialVersionUID = 29547966603609985L;

  public GitConfigException(GitExceptionType exceptionType) {
    super(exceptionType.toString());
  }
  
  public static enum GitExceptionType {
    NOT_INSTALLED("Git is not intalled in this computer");
    
    
    private String message;
    
    GitExceptionType(String message) {
      this.message = message;
    }
    
    public String toString() {
      return this.message;
    }
    
  }
  
}
