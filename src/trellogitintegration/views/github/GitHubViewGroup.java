package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.wizard.properties.utils.GroupTemplate;

/**
 * This class creates a GitHub view group for the View UI 
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class GitHubViewGroup extends GroupTemplate {
  
  private final GitManager gitManager;
  private Label currentBranch;
  private Button status;
  private Button push;
  private Button switchBranch;
  private Button pullRequest;
  
  private Text outputArea;
  private Composite buttonsColumn;
  
  /**
   * Create a GitHub group for the viwe UI 
   * @param parent
   * @param gitManager
   * @throws Exception
   */
  public GitHubViewGroup(Composite parent, GitManager gitManager) throws Exception {
    super(parent, "Git", 2);
    ValidationUtils.checkNull(parent, gitManager);
    
    this.gitManager = gitManager;
    
    this.addBranchInfo();    
    this.addButtonGroup();    
    this.addButtonActionListeners();    
    this.addOutputArea();    
    
    ButtonResizeController buttonResizeController = new ButtonResizeController();
    this.addControlListener(buttonResizeController);
    this.addPaintListener(buttonResizeController);
  }

  /**
   * Update the branch name, if currentBranchName is null, it is assumed to be not
   * a git repo
   * @param currentBranchName the name of the branch, null if not a git repo
   */
  public void updateCurrentBranch(String currentBranchName) {
    if (currentBranchName != null) {
      this.currentBranch.setText("Current Branch: " + currentBranchName);
    } else {
      this.currentBranch.setText("This is not a git repo");
    }
  }
  
  /**
   * Update output area that shows execution result 
   * @param output output to be displayed
   */
  public void updateOutputArea(String output) {
    ValidationUtils.checkNull(output);
    this.outputArea.setText(output);
  }
  
  /**
   * Suppresses extension check
   */
  public void checkSubclass(){}
  
  /**
   * Display the branch information on the UI 
   */
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
  
  /**
   * Add the group of buttons for Git operations
   */
  private void addButtonGroup() {
    this.buttonsColumn = new Composite(this, SWT.NONE  );
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.buttonsColumn.setLayout(layout);
    
    this.status = new Button(this.buttonsColumn, SWT.PUSH);
    this.status.setText("Check Status");
    this.switchBranch = new Button(this.buttonsColumn, SWT.PUSH);
    this.switchBranch.setText("Checkout Branch");
    this.push = new Button(this.buttonsColumn, SWT.PUSH);
    this.push.setText("Push Changes");
    this.pullRequest = new Button(this.buttonsColumn, SWT.PUSH);
    this.pullRequest.setText("Submit Pull Request");
  }
  
  /**
   * Add output area for execution result
   */
  private void addOutputArea() {
    this.outputArea = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
    this.outputArea.setLayoutData( new GridData( GridData.FILL_BOTH ));
  }
  
  /**
   * Add button action listeners 
   * @throws Exception if git encounters problem when executing commands
   */
  private void addButtonActionListeners() throws Exception {
    MouseListener mouseListener = new GitHubOperationButtonListener(this, this.gitManager)
        .addOperation(GitOperation.ADD_ALL, null)
        .addOperation(GitOperation.COMMIT, "commit message")
        .addOperation(GitOperation.PUSH, null).withFixedArgument(this.gitManager.getCurrentBranch());
    this.push.addMouseListener(mouseListener);
    
    mouseListener = new GitHubOperationButtonListener(this, this.gitManager)
        .addOperation(GitOperation.STATUS, null);
    this.status.addMouseListener(mouseListener);    
    
  }  
  
  /**
   * Listener to listener for paint and resize to make all buttons' sizes uniforme
   * Created: Nov 3, 2016
   * @author Man Chon Kuok
   */
  private class ButtonResizeController implements PaintListener, ControlListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlMoved(ControlEvent e) {
      UIUtils.uniformButtonSize(buttonsColumn, status, push, switchBranch, pullRequest);      
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlResized(ControlEvent e) {
      UIUtils.uniformButtonSize(buttonsColumn, status, push, switchBranch, pullRequest);      
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintControl(PaintEvent e) {
      UIUtils.uniformButtonSize(buttonsColumn, status, push, switchBranch, pullRequest);      
    }
    
  }
}
