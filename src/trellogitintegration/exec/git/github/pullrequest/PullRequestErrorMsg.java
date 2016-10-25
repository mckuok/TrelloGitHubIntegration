package trellogitintegration.exec.git.github.pullrequest;

import java.util.List;

public class PullRequestErrorMsg {

  private List<Error> errors;
  
  public PullRequestErrorMsg(){};
  
  private static class Error {
    private String message;
    
    public Error(){};
    
    public String getMessage() {
      return message;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((message == null) ? 0 : message.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Error other = (Error) obj;
      if (message == null) {
        if (other.message != null)
          return false;
      } else if (!message.equals(other.message))
        return false;
      return true;
    }
    
  }
  
  public List<Error> getErrors() {
    return errors;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PullRequestErrorMsg other = (PullRequestErrorMsg) obj;
    if (errors == null) {
      if (other.errors != null)
        return false;
    } else if (!errors.equals(other.errors))
      return false;
    return true;
  }

    
}
