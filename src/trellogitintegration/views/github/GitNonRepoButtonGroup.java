package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.views.github.action.GitOperationActionBuilder;

public class GitNonRepoButtonGroup extends ButtonGroup {

  private Button init;
  private final GitManager gitManager;
  
  public GitNonRepoButtonGroup(GitRepoViewGroup parent, GitManager gitManager) {
    super(parent);
    this.gitManager = gitManager;
  }

  @Override
  protected void addButtonsToContainer(Composite container) {
    this.init = new Button(container, SWT.PUSH);
    this.init.setText("Init local repo");
    
  }

  @Override
  protected void addActionListeners() throws Exception {
    MouseListener mouseListener = GitOperationActionBuilder
        .create(this.getViewGroup(), this.gitManager).addOperation(GitOperation.INIT)
        .build();

    this.init.addMouseListener(mouseListener);

    
  }

  @Override
  protected Button[] getButtons() {
    return new Button[]{this.init};
  }

}
