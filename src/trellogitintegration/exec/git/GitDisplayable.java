package trellogitintegration.exec.git;

import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.InputType;

public class GitDisplayable {

  public static String getInstruction(GitOperation operation) {
    ValidationUtils.checkNull(operation);
    
    switch(operation) {
    case INIT:
      return "Initialize a git repo";
    case PUSH:
      return "Push to remote repo";
    case PULL:
      return "Pull from remote reop";
    case ADD:
      return "Add files to commit";
    case ADD_ALL:
      return "Add all files to commit";
    case COMMIT:
      return "Commit changes";
    case NEW_BRANCH:
      return "Create a new branch";
    case CHECKOUT_BRANCH:
      return "Checkout a branch";
    case VERSION:
      return "Get version";
    case STATUS:
      return "Get status";
    case LOG:
      return "Get log";
    case CLONE:
      return "Clone from a remote repo";
    case BRANCH:
      return "List all branches";
    default:
      throw new UnsupportedOperationException();
    }
  }
  
  public static String getArgumentHintText(GitOperation operation) {
    ValidationUtils.checkNull(operation);
    
    switch(operation) {
    case PUSH:
      return "Branch name to push to";
    case PULL:
      return "Branch to pull from";
    case ADD:
      return "File to add to commit";
    case COMMIT:
      return "Commit message";
    case NEW_BRANCH:
      return "New branch name";
    case CHECKOUT_BRANCH:
      return "Name of the branch to checkout to";
    case CLONE:
      return "Repo URL to clone from";
    case VERSION:
    case STATUS:
    case LOG:
    case BRANCH:
    case ADD_ALL:
    case INIT:
      return "";
    default:
      throw new UnsupportedOperationException();
    }
  }
  
  public static InputType getInputType(GitOperation operation) {
    ValidationUtils.checkNull(operation);
    
    switch(operation) {
    case PUSH:
    case PULL:
    case ADD:
    case COMMIT:
    case CLONE:
    case NEW_BRANCH:
      return InputType.TEXT;
    case CHECKOUT_BRANCH:
      return InputType.RADIO_BUTTON;
    case VERSION:
    case STATUS:
    case LOG:
    case BRANCH:
    case ADD_ALL:
    case INIT:
      return InputType.NONE;
    default:
      throw new UnsupportedOperationException();
    }
    
  }
  
}
