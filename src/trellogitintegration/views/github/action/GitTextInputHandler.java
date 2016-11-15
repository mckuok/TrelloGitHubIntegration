package trellogitintegration.views.github.action;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.git.GitDisplayable;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.github.CommandDisplayer;

/**
 * This class creates a text box for user input for the given git operation's
 * argument
 * 
 * Created: Nov 5, 2016
 * 
 * @author Man Chon Kuok
 */
public class GitTextInputHandler extends GitInputHandler {

  private Text texbox;

  /**
   * Creates a text input handler that takes in user input using a text box
   * 
   * @param operation
   *          git operation for this text input
   * @param argument
   *          default argument for this git operation, empty if none
   */
  public GitTextInputHandler(GitOperation operation, String argument) {
    super(operation, argument);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addToGUI(Composite parent) {
    ValidationUtils.checkNull(parent);

    if (this.needsUserInput()) {
      UIUtils.addLabel(parent,
          GitDisplayable.getInstruction(this.getOperation()));
      GridData gridData = new GridData();
      gridData.widthHint = 250;
      this.texbox = UIUtils.addInputTextBox(parent, gridData, "", GitDisplayable.getArgumentHintText(this.getOperation()));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUserInput() {
    if (this.needsUserInput()) {
      return this.texbox.getText();
    } else {
      return this.getArgument();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addModifyListener(CommandDisplayer display) {
    if (this.needsUserInput()) {
      this.texbox.addModifyListener(new ModifyListener() {

        @Override
        public void modifyText(ModifyEvent e) {
          display.update();

        }

      });
    }
  }

}
