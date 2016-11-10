package trellogitintegration.views.github.action;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.OperationResult;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.github.CommandDisplayer;
import trellogitintegration.views.github.GitRepoViewGroup;
import trellogitintegration.views.github.UserInputDialogue;

/**
 * Listener in charge of performing git related operations when their button is
 * clicked
 * 
 * This object should be constructed using GitOperationActionBuilder
 * 
 * Created: Nov 3, 2016
 * 
 * @author Man Chon Kuok
 */
public class GitOperationButtonListener implements MouseListener {

  private final GitRepoViewGroup parent;
  private final List<InputHandler> inputHandlerList;
  private final boolean needShell;
  private final GitManager gitManager;

  /**
   * Create a Button Listener for GitHub operations
   * 
   * @param parent
   *          parent of the button where the result can be displayed
   * @param gitManager
   *          gitManager responsible for this project
   * @param inputHandlerList
   *          a list of InputHandler, each containing information about a
   *          GitOperaion
   * @param needShell
   *          true if the button should pop up a new shell for argument input
   */
  public GitOperationButtonListener(GitRepoViewGroup parent,
      GitManager gitManager, List<InputHandler> inputHandlerList,
      boolean needShell) {
    ValidationUtils.checkNull(parent, gitManager, inputHandlerList);

    this.parent = parent;
    this.gitManager = gitManager;
    this.inputHandlerList = inputHandlerList;
    this.needShell = needShell;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDoubleClick(MouseEvent e) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDown(MouseEvent e) {
  }

  /**
   * {@inheritDoc}
   * 
   * Creates a shell for user input, or execute the command if the command does
   * not require any user input
   */
  @Override
  public void mouseUp(MouseEvent e) {
    if (this.needShell) {
      Shell dialogue = this.createDialogue();

      UserInputDialogue inputDialogue = new UserInputDialogue(dialogue,
          this.inputHandlerList);
      inputDialogue.addInputFieldsToGUI();

      CommandDisplayer commandDisplay = new CommandDisplayer(dialogue,
          this.inputHandlerList);
      commandDisplay.addToGUI();

      inputDialogue.attachToDisplay(commandDisplay);

      UIUtils.addSeparator(dialogue);
      addConfirmationButton(dialogue);

      dialogue.setFocus();
      dialogue.pack();
      UIUtils.centerShell(this.parent, dialogue);
      dialogue.open();
    } else {
      executeCommandsForButton();
    }
  }

  /**
   * Add and set the behavior of the confirm button
   * 
   * @param parent
   *          parent of the confirm button
   */
  private void addConfirmationButton(Shell parent) {
    ValidationUtils.checkNull(parent);
    
    Button confirmButton = new Button(parent, SWT.PUSH);
    confirmButton.setText("Confirm");
    confirmButton
        .setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
    confirmButton.addMouseListener(new MouseListener() {

      @Override
      public void mouseDoubleClick(MouseEvent e) {
      }

      @Override
      public void mouseDown(MouseEvent e) {
      }

      @Override
      public void mouseUp(MouseEvent e) {
        executeCommandsForButton();
        parent.close();
      }

    });
  }

  /**
   * Create a shell for dialogue for argument input and a view to show commands
   * to be executed
   * 
   * @return a create shell
   */
  private Shell createDialogue() {
    Shell dialogue = new Shell(this.parent.getDisplay());
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    dialogue.setLayout(layout);

    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    dialogue.setLayoutData(data);

    dialogue.setText("Arguments");

    return dialogue;
  }

  /**
   * Executes the series of commands associated with this button. If one of the
   * commands failed to execute, the following commands do not get executed. The
   * output of the execution will get displayed in the output area.
   */
  private void executeCommandsForButton() {
    StringBuilder output = new StringBuilder();
    Iterator<InputHandler> inputIterator = this.inputHandlerList.iterator();

    try {
      while (inputIterator.hasNext()) {
        InputHandler handler = inputIterator.next();
        OperationResult<String> result = this.gitManager
            .execute(handler.getOperation(), handler.getUserInput());
        output.append(result.getDisplayableMessage()).append("\n");

        if (!result.isSuccessful()) {
          break;
        }
      }
    } catch (Exception exception) {
      output.append(exception.getMessage());
    }
    this.parent.updateOutputArea(output.toString());
    this.parent.updateCurrentBranch();
  }

}