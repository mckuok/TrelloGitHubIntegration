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
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.wizard.properties.utils.UIUtils;

public class GitHubOperationButtonListener implements MouseListener {

  private final Composite parent;
 
  private List<GitOperationToArgumentPlaceholderPair> gitOperationOrderList = new LinkedList<>();
  private List<Text> textboxLists = new ArrayList<>(); 
  
  private boolean needShell = false;
  private Button confirmButton;
  
  public GitHubOperationButtonListener(Composite parent) {
    this.parent = parent;
  }
  
  private GitHubOperationButtonListener(Composite parent, List<GitOperationToArgumentPlaceholderPair> gitOperationOrderList, boolean needShell) {
    this(parent);
    this.gitOperationOrderList.addAll(gitOperationOrderList);
    this.needShell = needShell;
  }
  
  public GitHubOperationButtonListener addOperation(GitOperation operation, String placeholder) {
    GitOperationToArgumentPlaceholderPair pair = new GitOperationToArgumentPlaceholderPair(operation, placeholder); 
    this.gitOperationOrderList.add(pair);

    if (pair.needArgument) {
      this.needShell = true;
    } 
    
    return new GitHubOperationButtonListener(this.parent, this.gitOperationOrderList, this.needShell);
  }
  
  @Override
  public void mouseDoubleClick(MouseEvent e) {}

  @Override
  public void mouseDown(MouseEvent e) {}

  @Override
  public void mouseUp(MouseEvent e) {
    if (this.needShell) {
      Shell dialogue = this.getDialogue();
      Composite inputSection = this.getInputSection(dialogue);
      
      final GridData gridData = new GridData();
      gridData.widthHint = 250;
      
      Iterator<GitOperationToArgumentPlaceholderPair> iterator = this.gitOperationOrderList.iterator();
      
      while(iterator.hasNext()) {
        GitOperationToArgumentPlaceholderPair pair = iterator.next();
        if (pair.needArgument) {
          UIUtils.addLabel(inputSection, pair.operation.getDisplayable());
          Text textbox = UIUtils.addInputTextBox(inputSection, gridData, "");
          textbox.setMessage(pair.placeholder);
          this.textboxLists.add(textbox);
        } else {
          this.textboxLists.add(null);
        }
      }
            
      displayExecutingCommands(dialogue);
      
      
      this.confirmButton = new Button(dialogue, SWT.PUSH);
      this.confirmButton.setText("Confirm");
      this.confirmButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
      
      dialogue.setFocus();
      dialogue.pack();
      this.centerShell(dialogue);
      dialogue.open();
    }
    
    
  }
  
  private void displayExecutingCommands(Composite parent) {
    List<String> commands = new ArrayList<>();
    this.gitOperationOrderList.stream().forEach(gitOperationPair -> commands.add(gitOperationPair.operation.getCommand()));
    ExecutingCommandsDisplay commandDisplay = new ExecutingCommandsDisplay(parent, commands);
    
    for (int i = 0; i < this.textboxLists.size(); i++) {
      Text textbox = this.textboxLists.get(i);
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

  private Shell getDialogue() {
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
  
  private Composite getInputSection(Composite parent) {
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
  
  private void centerShell(Shell shell) {
    Monitor primary = this.parent.getDisplay().getPrimaryMonitor();
    Rectangle bounds = primary.getBounds();
    Rectangle rect = shell.getBounds();
    
    int x = bounds.x + (bounds.width - rect.width) / 2;
    int y = bounds.y + (bounds.height - rect.height) / 2;
    
    shell.setLocation(x, y);
  }
  
  private class GitOperationToArgumentPlaceholderPair {
    
    private final GitOperation operation;
    private final boolean needArgument;
    private final String placeholder;
    
    public GitOperationToArgumentPlaceholderPair(GitOperation operation, String placeholder) {
      this.needArgument = placeholder != null;
      this.operation = operation;
      this.placeholder = placeholder;
    }
  }

  
}
