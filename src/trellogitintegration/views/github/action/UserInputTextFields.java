package trellogitintegration.views.github.action;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.github.CommandDisplayer;

/**
 * This class creates an input dialogue for user input. The input field is
 * defined by the list of input handlers.
 * 
 * Created: Nov 6, 2016
 * 
 * @author Man Chon Kuok
 */
public class UserInputTextFields {

  private final Composite parent;
  private List<GitInputHandler> inputHandlers = new LinkedList<>();

  /**
   * Creates an InputDialogue
   * 
   * @param parent
   *          parent of this dialogue
   * @param inputHandlers
   *          a list of input handlers that need to be displayed in this
   *          dialogue
   */
  public UserInputTextFields(Composite parent, List<GitInputHandler> inputHandlers) {
    ValidationUtils.checkNull(parent, inputHandlers);

    this.parent = parent;
    this.inputHandlers = inputHandlers;

  }

  /**
   * Add all the input fields to the GUI
   */
  public void addInputFieldsToGUI() {
    Composite container = UIUtils.createContainer(this.parent, 2);

    Iterator<GitInputHandler> inputFieldsIterator = this.inputHandlers.iterator();
    while (inputFieldsIterator.hasNext()) {
      inputFieldsIterator.next().addToGUI(container);
    }
  }

  /**
   * Attach a display to the input dialogue so all the changes from user input
   * can be reflected on the display
   * 
   * @param display
   *          display used for displaying commands
   */
  public void attachToDisplay(CommandDisplayer display) {
    ValidationUtils.checkNull(display);

    Iterator<GitInputHandler> inputFieldsIterator = this.inputHandlers.iterator();
    while (inputFieldsIterator.hasNext()) {
      inputFieldsIterator.next().attachToDisplay(display);
    }
  }

}
