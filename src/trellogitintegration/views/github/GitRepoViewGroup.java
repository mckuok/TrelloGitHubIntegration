
package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.wizard.properties.utils.GroupTemplate;

/**
 * This class creates a GitHub view group for the View UI
 * 
 * Created: Nov 3, 2016
 * 
 * @author Man Chon Kuok
 */
public class GitRepoViewGroup extends GroupTemplate {

  private final GitManager gitManager;
  private Label currentBranch;
  
  private Text outputArea;
  
  /**
   * Create a GitHub group for the view UI
   * 
   * @param parent
   * @param gitManager
   * @throws Exception
   */
  public GitRepoViewGroup(Composite parent, GitManager gitManager)
      throws Exception {
    super(parent, "Git", 2);
    ValidationUtils.checkNull(parent, gitManager);

    this.gitManager = gitManager;

   
    this.addBranchInfo();
    
    ButtonGroup buttonGroup = null;
    if (this.gitManager.status().isSuccessful()) {
      buttonGroup = new GitRepoButtonGroup(this, this.gitManager);
    } else {
      buttonGroup = new GitNonRepoButtonGroup(this, this.gitManager);
    }
    
    buttonGroup.addToGUI();    
    this.addControlListener(buttonGroup.getResizeController());
    this.addPaintListener(buttonGroup.getResizeController());
    this.addOutputArea();

    
  }

  /**
   * Update the branch name, if currentBranchName is null, it is assumed to be
   * not a git repo
   * 
   * @param currentBranchName
   *          the name of the branch, null if not a git repo
   */
  public void updateCurrentBranch() {
    String currentBranchName = "";
    try {
      currentBranchName = this.gitManager.getCurrentBranch();
    } catch (Exception e2) {
      currentBranchName = "Unable to resolve current branch";
    }    
    if (currentBranchName != null) {
      this.currentBranch.setText("Current Branch: " + currentBranchName);
    } else {
      this.currentBranch.setText("This is not a git repo");
    }
  }

  /**
   * Update output area that shows execution result
   * 
   * @param output
   *          output to be displayed
   */
  public void updateOutputArea(String output) {
    ValidationUtils.checkNull(output);
    this.outputArea.setText(output);
  }

  /**
   * Suppresses extension check
   */
  public void checkSubclass() {
  }

  /**
   * Display the branch information on the UI
   */
  private void addBranchInfo() {
    this.currentBranch = new Label(this, SWT.NONE);
    this.updateCurrentBranch();
    UIUtils.addLabel(this, "");
  }

  /**
   * Add output area for execution result
   */
  private void addOutputArea() {
    this.outputArea = new Text(this,
        SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
    this.outputArea.setLayoutData(new GridData(GridData.FILL_BOTH));
  }


}
