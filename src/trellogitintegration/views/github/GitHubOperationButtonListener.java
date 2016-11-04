package trellogitintegration.views.github;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.OperationResult;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.utils.ValidationUtils;

/**
 * Listener in charge of performing git related operations when their button 
 * is clicked
 * TODO refactor this class further
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class GitHubOperationButtonListener implements MouseListener {

  private final GitHubViewGroup parent;
 
  private List<GitOperationToHintTextPair> gitOperationOrderList = new LinkedList<>();
  private List<Text> textboxList = new ArrayList<>(); 
  private List<String> argumentList = new ArrayList<>();
  
  private boolean needShell = false;
  private Button confirmButton;
  private GitManager gitManager;
  
  /**
   * Create a Button Listener for GitHub operations
   * @param parent parent of the button where the result can be displayed
   * @param gitManager gitManager responsible for this project
   */
  public GitHubOperationButtonListener(GitHubViewGroup parent, GitManager gitManager) {
    ValidationUtils.checkNull(parent, gitManager);
    
    this.parent = parent;
    this.gitManager = gitManager;
  }
  
  /**
   * Private constructors to carry needed parameters for initialization
   * @param parent
   * @param gitManager
   * @param gitOperationOrderList
   * @param fixedArgument
   * @param needShell
   */
  private GitHubOperationButtonListener(GitHubViewGroup parent, GitManager gitManager, List<GitOperationToHintTextPair> gitOperationOrderList, List<String> fixedArgument, boolean needShell) {
    this(parent, gitManager);
    ValidationUtils.checkNull(gitOperationOrderList, fixedArgument);
    
    this.gitOperationOrderList.addAll(gitOperationOrderList);
    this.argumentList.addAll(fixedArgument);
    this.needShell = needShell;
    
  }
  
  /**
   * Add operation this button needs to perform
   * @param operation operation to be performed
   * @param hintText hint text for the argument, null if argument does not come from user input
   * @return the new GitHubOperationButtonListener with the new operation added.
   */
  public GitHubOperationButtonListener addOperation(GitOperation operation, String hintText) {
    ValidationUtils.checkNull(operation);
    
    GitOperationToHintTextPair pair = new GitOperationToHintTextPair(operation, hintText); 
    this.gitOperationOrderList.add(pair);
    this.argumentList.add("");
    
    if (pair.needArgument) {
      this.needShell = true;
    }     
    
    return new GitHubOperationButtonListener(this.parent, this.gitManager, this.gitOperationOrderList, this.argumentList, this.needShell);
  }
  
  /**
   * Add default argument to the previously added operation
   * @param argument argument for the previously added operation
   * @return the new GitHubOperationButtonListener with the new argument needed for the previous operation
   */
  public GitHubOperationButtonListener withFixedArgument(String argument) {
    //ValidationUtils.checkNull(argument);
    if (this.argumentList.size() != this.gitOperationOrderList.size()) {
      throw new IllegalArgumentException("withFixedArgument() can only be called after addOperation()");
    }
    
    
    this.argumentList.set(this.argumentList.size() - 1, argument);
    return new GitHubOperationButtonListener(this.parent, this.gitManager, this.gitOperationOrderList, this.argumentList, this.needShell);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDoubleClick(MouseEvent e) {}
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDown(MouseEvent e) {}
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseUp(MouseEvent e) {
    if (this.needShell) {
      Shell dialogue = this.createDialogue();
      Composite inputSection = this.createInputSection(dialogue);
      
      addArgumentInputTextbox(inputSection);
            
      displayExecutingCommands(dialogue);      
      UIUtils.addSeparator(dialogue);
      setConfirmationButton(dialogue);
      
      dialogue.setFocus();
      dialogue.pack();
      UIUtils.centerShell(this.parent, dialogue);
      dialogue.open();      
      
    } else {
      executeCommandsForButton();
    }       
  }
  
  /**
   * Add argument input textbox to parent as needed depending on the operation
   * @param parent parent for the textboxes
   */
  private void addArgumentInputTextbox(Composite parent) {
    final GridData gridData = new GridData();
    gridData.widthHint = 250;
    
    Iterator<GitOperationToHintTextPair> iterator = this.gitOperationOrderList.iterator();
    
    while(iterator.hasNext()) {
      GitOperationToHintTextPair pair = iterator.next();
      if (pair.needArgument) {
        UIUtils.addLabel(parent, pair.operation.getDisplayable());
        Text textbox = UIUtils.addInputTextBox(parent, gridData, "");
        textbox.setMessage(pair.hintText);
        this.textboxList.add(textbox);
      } else {
        this.textboxList.add(null);
      }
    }
  }
  
  /**
   * Set the behavior of the confirm button
   * @param parent parent of the confirm button
   */
  private void  setConfirmationButton(Composite parent) {
    this.confirmButton = new Button(parent, SWT.PUSH);
    this.confirmButton.setText("Confirm");
    this.confirmButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
    this.confirmButton.addMouseListener(new MouseListener() {

      @Override
      public void mouseDoubleClick(MouseEvent e) {}

      @Override
      public void mouseDown(MouseEvent e) {}

      @Override
      public void mouseUp(MouseEvent e) {
        executeCommandsForButton();
      }
      
    });
  }
  
  /**
   * Display the commands that are about to get executed. 
   * The display gets updated as user typed in the argument
   * @param parent 
   */
  private void displayExecutingCommands(Composite parent) {
    List<String> commands = new ArrayList<>();
    this.gitOperationOrderList.stream().forEach(gitOperationPair -> commands.add(gitOperationPair.operation.getCommand()));
    ExecutingCommandsDisplay commandDisplay = new ExecutingCommandsDisplay(parent, commands, this.argumentList);
    
    for (int i = 0; i < this.textboxList.size(); i++) {
      Text textbox = this.textboxList.get(i);
      if (textbox != null) {
        final int index = i;
        textbox.addModifyListener(new ModifyListener() {

          @Override
          public void modifyText(ModifyEvent e) {
            commandDisplay.update(index, textbox.getText());
            
          }
          
        });
      }
    }
    
  }

  /**
   * Create a shell for dialogue for argument input and a view to show commands
   * to be executed
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
   * Creates an input section for operations that needs argument input
   * @param parent parent of the argument input section
   * @return the formatted composite
   */
  private Composite createInputSection(Composite parent) {
    ValidationUtils.checkNull(parent);
    
    Composite inputSection = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    inputSection.setLayout(layout);
    
    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    inputSection.setLayoutData(data);
    
    return inputSection;
        
  }
  
  /**
   * Executes the series of commands associated with this button. If one of the 
   * commands failed to execute, the following commands do not get executed.
   * The output of the execution will get displayed in the output area.
   */
  private void executeCommandsForButton() {
    int index = 0;
    StringBuilder output = new StringBuilder();
    Iterator<GitOperationToHintTextPair> iterator = this.gitOperationOrderList.iterator();
    try {
      while (iterator.hasNext()) {
        GitOperationToHintTextPair pair = iterator.next();
        OperationResult<String> result = null;
        String argument = null;
        if (pair.needArgument) {
          argument = this.textboxList.get(index).getText();
        }
        result = this.gitManager.execute(pair.operation, argument);
        output.append(result.getDisplayableMessage());
        
        if (!result.isSuccessful()) {
          break;
        }
        index ++;
      }
    }
    catch(Exception exception) {
     output.append(exception.getMessage()); 
    }
    this.parent.updateOutputArea(output.toString());
  }
  
  /**
   * Data structure to hold the Git Operation and the needed hint text
   * Created: Nov 3, 2016
   * @author Man Chon Kuok
   */
  private class GitOperationToHintTextPair {
    
    private final GitOperation operation;
    private final boolean needArgument;
    private final String hintText;
    
    public GitOperationToHintTextPair(GitOperation operation, String hintText) {
      ValidationUtils.checkNull(operation);
      
      this.needArgument = hintText != null;
      this.operation = operation;
      this.hintText = hintText;
    }
  }

  
}
