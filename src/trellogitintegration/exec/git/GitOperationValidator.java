package trellogitintegration.exec.git;

public class GitOperationValidator {

  public static boolean validateOperation(GitOperation operation, String output) {
    switch (operation) {
    case INIT:
      return validateInit(output);
    case PUSH:
      return validatePush(output);
    case PULL:
      return validatePull(output);
    case ADD:
      return validateAdd(output);
    case ADD_ALL:
      return validateAdd(output);
    case COMMIT:
      return validateCommit(output);
    case NEW_BRANCH:
      return validateNewBranch(output);
    case CHECKOUT_BRANCH:
      return validateCheckOutBranch(output);
    case VERSION:
      return validateVersion(output);
    case STATUS:
      return validateStatus(output);
    case LOG:
      return validateLog(output);
    case CLONE:
      return validateClone(output);
    case BRANCH:
      return validateBranch(output);
    default:
      throw new UnsupportedOperationException();
    }
  }
  
  private static boolean validateInit(String output) {
    return output.startsWith("Initialized empty Git repository");
  }
  
  private static boolean validatePush(String output) {
    return output.contains("Writing objects");
  }
  
  private static boolean validatePull(String output) {
    return output.startsWith("From");
  }
  
  private static boolean validateAdd(String output) {
    return output.replace("\n", "").isEmpty();
  }
  
  private static boolean validateCommit(String output) {
    return output.contains("file changed") || output.contains("files changed");
  }
  
  private static boolean validateNewBranch(String output) {
    return output.contains("Switched to a new branch");
  }
  
  private static boolean validateCheckOutBranch(String output) {
    return output.contains("Switched to branch");
  }
  
  private static boolean validateVersion(String output) {
    return output.startsWith("git version");
  }
  
  private static boolean validateStatus(String output) {
    return output.startsWith("On branch ");
  }
  
  private static boolean validateLog(String output) {
    return output.startsWith("commit");
  }
  
  private static boolean validateClone(String output) {
    return output.startsWith("Cloning into");
  }
  
  private static boolean validateBranch(String output) {
    return output.contains("*");
  }
}
