package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.views.github.action.GitOperationActionBuilder;
import trellogitintegration.views.github.action.PullRequestHandler;

/**
 * This class is in charge of creating the button group with git related operations
 * Created: Nov 10, 2016
 * @author Man Chon Kuok
 */
public class GitRepoButtonGroup extends ButtonGroup {

  private final GitManager gitManager;
  
  private Button status;
  private Button push;
  private Button switchBranch;
  private Button pullRequest;
  private Button log;

  /**
   * Creates a button group for git repo
   * @param viewGroup 
   * @param gitManager
   */
  public GitRepoButtonGroup(GitRepoViewGroup viewGroup, GitManager gitManager) {
    super(viewGroup);
    this.gitManager = gitManager;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void addButtonsToContainer(Composite container) {
    this.status = new Button(container, SWT.PUSH);
    this.status.setText("Check Status");

    this.switchBranch = new Button(container, SWT.PUSH);
    this.switchBranch.setText("Checkout Branch");

    this.push = new Button(container, SWT.PUSH);
    this.push.setText("Push Changes");

    this.pullRequest = new Button(container, SWT.PUSH);
    this.pullRequest.setText("Submit Pull Request");

    this.log = new Button(container, SWT.PUSH);
    this.log.setText("Log");
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addActionListeners() throws Exception {
    String currentBranch = this.gitManager.getCurrentBranch();
    
    MouseListener mouseListener = GitOperationActionBuilder
        .create(this.getViewGroup(), this.gitManager).addOperation(GitOperation.ADD_ALL)
        .addOperation(GitOperation.COMMIT).thatNeedsUserInput()
        .addOperation(GitOperation.PUSH)
        .withFixedArgument(currentBranch)
        .build();

    this.push.addMouseListener(mouseListener);

    mouseListener = GitOperationActionBuilder.create(this.getViewGroup(), this.gitManager)
        .addOperation(GitOperation.STATUS).build();
    this.status.addMouseListener(mouseListener);

    mouseListener = GitOperationActionBuilder.create(this.getViewGroup(), this.gitManager)
        .addOperation(GitOperation.CHECKOUT_BRANCH).thatNeedsUserInput()
        .withDefaultOptions(this.gitManager.getAllbranches())
        .build();
    this.switchBranch.addMouseListener(mouseListener);

    mouseListener = GitOperationActionBuilder.create(this.getViewGroup(), this.gitManager)
        .addOperation(GitOperation.LOG).build();
    this.log.addMouseListener(mouseListener);

    this.pullRequest.addMouseListener(new PullRequestHandler(this.getViewGroup(), this.gitManager));
    
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Button[] getButtons() {
    return new Button[] {this.status, this.switchBranch, this.pullRequest, this.push, this.log};
  }


}
