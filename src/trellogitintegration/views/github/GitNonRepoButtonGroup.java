package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import trellogitintegration.exec.ResultCallback;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.views.github.action.GitOperationActionBuilder;

/**
 * Creates a button group for non git repo project
 * Created: Nov 10, 2016
 * @author Man Chon Kuok
 */
public class GitNonRepoButtonGroup extends ButtonGroup {

  private Button init;
  private final GitManager gitManager;
  
  /**
   * Ceates a button group for non git repo project
   * @param viewGroup
   * @param gitManager
   */
  public GitNonRepoButtonGroup(GitRepoViewGroup viewGroup, GitManager gitManager) {
    super(viewGroup);
    this.gitManager = gitManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addButtonsToContainer(Composite container) {
    this.init = new Button(container, SWT.PUSH);
    this.init.setText("Init local repo");
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addActionListeners() throws Exception {
    MouseListener mouseListener = GitOperationActionBuilder
        .create(this.getViewGroup(), this.gitManager).addOperation(GitOperation.INIT)
        .addOperationCallback(new ResultCallback() {

          @Override
          public void callback(String result) {
            try {
              getViewGroup().updateButtonGroup();
            } catch (Exception e) {
              e.printStackTrace();
            }            
          }
          
        })
        .build();

    this.init.addMouseListener(mouseListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Button[] getButtons() {
    return new Button[]{this.init};
  }

}
