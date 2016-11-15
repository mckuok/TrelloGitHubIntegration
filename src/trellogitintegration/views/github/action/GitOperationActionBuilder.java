package trellogitintegration.views.github.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import trellogitintegration.exec.ResultCallback;
import trellogitintegration.exec.git.GitDisplayable;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.InputType;
import trellogitintegration.views.github.GitRepoViewGroup;

/**
 * This class is intended to build a GitOperationButtionListener object that
 * fully addresses different situation, including if a Button needs to handle
 * multiple operations, some needs user input and some don't, some needs
 * argument but doesn't need to come from the user.
 * 
 * ex)
 * 
 * MouseListener mouseListener = GitOperationActionBuilder.create(this, this.gitManager) 
 *  .addOperation(GitOperation.ADD_ALL)
 *  .addOperation(GitOperation.COMMIT).thatNeedsUserInput()
 *  .addOperation(GitOperation.PUSH).withFixedArgument(this.gitManager.getCurrentBranch())
 *  .build(); 
 *  
 *  Created: Nov 6, 2016
 * 
 * @author Man Chon Kuok
 */
public class GitOperationActionBuilder {

  private List<GitOperation> gitOperationList = new LinkedList<>();
  private List<String> argumentList = new ArrayList<>();
  private List<String[]> optionList = new ArrayList<>();
  private List<ResultCallback> callbackList = new ArrayList<>();
  private GitRepoViewGroup viewGroup;
  private GitManager gitManager;
  private boolean needShell = false;

  /**
   * Private constructor to carry states
   * 
   * @param viewGroup parent of the button
   * @param gitManager gitManager for the project
   */
  private GitOperationActionBuilder(GitRepoViewGroup viewGroup,
      GitManager gitManager) {
    ValidationUtils.checkNull(viewGroup, gitManager);

    this.viewGroup = viewGroup;
    this.gitManager = gitManager;
  }

  /**
   * Create a default Button Listener with no operations attached
   * 
   * @param parent
   *          parent of the button where the result can be displayed
   * @param gitManager
   *          gitManager responsible for this project
   */
  public static GitOperationActionBuilder create(GitRepoViewGroup parent,
      GitManager gitManager) {
    return new GitOperationActionBuilder(parent, gitManager);
  }

  /**
   * Add an operation that this button needs to perform
   * 
   * @param operation
   *          operation to be performed
   * @return the new GitOperationActionBuilder with the new operation added.
   */
  public GitOperationActionBuilder addOperation(GitOperation operation) {
    ValidationUtils.checkNull(operation);

    this.gitOperationList.add(operation);
    this.argumentList.add("");
    this.optionList.add(new String[] {});
    return this;
  }

  /**
   * Signifies that the previous addOperation(operation) needs user input
   * @return a GitOperationActionBuilder with all the previously set properties
   */
  public GitOperationActionBuilder thatNeedsUserInput() {
    if (this.argumentList.size() != this.gitOperationList.size()) {
      throw new IllegalArgumentException(
          "thatNeedsArgument() can only be called after addOperation()");
    }

    this.needShell = true;
    return this;
  }

  /**
   * Add default argument to the previously added operation
   * 
   * @param argument
   *          argument for the previously added operation
   * @return the new GitOperationActionBuilder with the new argument needed
   *         for the previous operation
   */
  public GitOperationActionBuilder withFixedArgument(String argument) {
    // ValidationUtils.checkNull(argument);
    if (this.argumentList.size() != this.gitOperationList.size()) {
      throw new IllegalArgumentException(
          "withFixedArgument() can only be called after addOperation()");
    }

    this.argumentList.set(this.argumentList.size() - 1, argument);
    return this;
  }

  /**
   * Useful when you want to add default options for user to choose instead 
   * of for them to type in
   * @param options options for users to choose from
   * @return a GitOperationActionBuilder with all the previously set properties
   */
  public GitOperationActionBuilder withDefaultOptions(String[] options) {
    if (this.optionList.size() != this.gitOperationList.size()) {
      throw new IllegalArgumentException(
          "withDefaultOptions() can only be called after addOperation()");
    }

    this.optionList.set(this.optionList.size() - 1, options);
    return this;
  }
  
  public GitOperationActionBuilder addOperationCallback(ResultCallback... callbacks) {
    this.callbackList.addAll(Arrays.asList(callbacks));    
    return this;
  }
  
  /**
   * Build the GitOperationButtonListener using the previous settings
   * @return a GitOperationButtonListener with all the settings configured
   */
  public GitOperationButtonListener build() {
    List<GitInputHandler> handlers = new LinkedList<>();

    Iterator<GitOperation> operationIterator = this.gitOperationList.iterator();
    Iterator<String> argumentIterator = this.argumentList.iterator();
    Iterator<String[]> optionIterator = this.optionList.iterator();

    while (operationIterator.hasNext()) {
      GitOperation operation = operationIterator.next();
      String argument = argumentIterator.next();
      String[] options = optionIterator.next();

      if (GitDisplayable.getInputType(operation).equals(InputType.TEXT)
          || GitDisplayable.getInputType(operation).equals(InputType.NONE)) {
        handlers.add(new GitTextInputHandler(operation, argument));
      } else {
        handlers.add(new GitButtonInputHandler(operation, options));
      }
    }

    return new GitOperationButtonListener(this.viewGroup, this.gitManager, handlers,
        this.callbackList, this.needShell);
  }

}
