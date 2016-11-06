package trellogitintegration.views.github;

import org.eclipse.swt.widgets.Composite;

import trellogitintegration.exec.git.GitDisplayable;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.utils.ValidationUtils;

/**
 * Abstract class for input handler for taking user input for a given git
 * operation
 * 
 * Created: Nov 6, 2016
 * 
 * @author Man Chon Kuok
 */
public abstract class InputHandler {

  private boolean needsUserInput;
  protected String argument;
  private GitOperation operation;

  /**
   * Creates an InputHandler that is in charge of the given operation with the
   * given argument
   * 
   * @param operation
   *          git operation for this handler
   * @param argument
   *          argument for this operation, can be empty if it needs user input
   */
  public InputHandler(GitOperation operation, String argument) {
    ValidationUtils.checkNull(operation);

    this.argument = argument == null ? "" : argument;
    if (!GitDisplayable.getArgumentHintText(operation).isEmpty()
        && (argument == null || argument.isEmpty())) {
      this.needsUserInput = true;
    } else {
      this.needsUserInput = false;
    }
    this.operation = operation;
  }

  /**
   * Add this input handler to the UI
   * 
   * @param parent
   *          parent of the UI
   */
  public abstract void addToGUI(Composite parent);

  /**
   * @return the user input to replace the argument
   */
  public abstract String getUserInput();

  /**
   * @return git operation for this handler
   */
  public GitOperation getOperation() {
    return this.operation;
  }

  /**
   * Attach this handler to the display
   * 
   * @param display
   *          command displayer that displays the changes from user input
   */
  public void attachToDisplay(CommandDisplayer display) {
    ValidationUtils.checkNull(display);

    this.addModifyListener(display);
  }

  /**
   * Updates the display when there are any changes done by the user to this
   * handler
   * 
   * @param display
   *          command displayer that displays the changes from user input
   */
  protected abstract void addModifyListener(CommandDisplayer display);

  /**
   * @return true of the operation requires user input, false otherwise
   */
  protected boolean needsUserInput() {
    return this.needsUserInput;
  }

  /**
   * @return the argument supplied from the constructor
   */
  protected String getArgument() {
    return this.argument;
  }

}
