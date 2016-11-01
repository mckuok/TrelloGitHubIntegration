package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.wizard.properties.utils.GroupTemplate;
import trellogitintegration.wizard.properties.utils.UIUtils;

public class GitHubViewGroup extends GroupTemplate {

  private final GitManager gitManager;
  private Label currentBranch;
  private Button status;
  private Button push;
  private Button switchBranch;
  private Button pullRequest;
  
  private Text outputArea;
  
  public GitHubViewGroup(Composite parent, GitManager gitManager) {
    super(parent, "Git");
    this.gitManager = gitManager;
    
    this.addBranchInfo();
    
    this.addButtonGroup();
    
    this.addButtonActionListeners();
    
    this.addOutputArea();
    
    this.redraw();
  }

  public void updateCurrentBranch(String currentBranchName) {
    if (currentBranchName != null) {
      this.currentBranch.setText("Current Branch: " + currentBranchName);
    } else {
      this.currentBranch.setText("This is not a git repo");
    }
  }
  
  public void updateOutputArea(String output) {
    System.out.println(output);
    this.outputArea.setText(output);
  }
  
  
  public void checkSubclass(){}
  
  
  private void addBranchInfo() {
    String currentBranchName = "";
    try {
      currentBranchName = this.gitManager.getCurrentBranch();
    } catch (Exception e2) {
      currentBranchName = "Unable to resolve current branch";
    }
    this.currentBranch = new Label(this, SWT.NONE);
    this.updateCurrentBranch(currentBranchName);
    
    UIUtils.addLabel(this, "");
  }
  
  private void addButtonGroup() {
    Composite buttonsColumn = new Composite(this, SWT.NONE  );
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    buttonsColumn.setLayout(layout);
    
    this.status = new Button(buttonsColumn, SWT.PUSH);
    this.status.setText("Check Status");
    this.switchBranch = new Button(buttonsColumn, SWT.PUSH);
    this.switchBranch.setText("Checkout Branch");
    this.push = new Button(buttonsColumn, SWT.PUSH);
    this.push.setText("Push Changes");
    this.pullRequest = new Button(buttonsColumn, SWT.PUSH);
    this.pullRequest.setText("Submit Pull Request");
 
    
    this.layout();
    UIUtils.uniformButtonSize(buttonsColumn, this.status, this.switchBranch, this.push, this.pullRequest);
  }
  
  private void addOutputArea() {
    this.outputArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
    this.outputArea.setLayoutData( new GridData( GridData.FILL_BOTH ));
  }
  
  private void addButtonActionListeners() {
    MouseListener mouseListener = new GitHubOperationButtonListener(this)
        .addOperation(GitOperation.ADD_ALL, null)
        .addOperation(GitOperation.COMMIT, "commit message")
        .addOperation(GitOperation.PUSH, null);
    
    this.push.addMouseListener(mouseListener);
  }  
  
}
