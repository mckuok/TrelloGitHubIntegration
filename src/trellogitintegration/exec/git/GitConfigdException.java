package trellogitintegration.exec.git;

public class GitConfigdException extends Exception {

  public GitConfigdException(GitExceptionType exceptionType) {
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
