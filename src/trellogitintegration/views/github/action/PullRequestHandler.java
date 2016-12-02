package trellogitintegration.views.github.action;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.OperationResult;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.exec.git.github.GitHubApiCaller;
import trellogitintegration.exec.git.github.pullrequest.PullRequest;
import trellogitintegration.exec.git.github.pullrequest.PullRequestResultMsg;
import trellogitintegration.persist.config.ProjectConfig.GitConfig;
import trellogitintegration.views.github.GitRepoViewGroup;

public class PullRequestHandler implements MouseListener {
  
  private final GitRepoViewGroup parent;
  private final GitManager gitManager;
  
  public PullRequestHandler(GitRepoViewGroup parent, GitManager gitManager) {
    this.parent = parent;
    this.gitManager = gitManager;
  }

  @Override
  public void mouseDoubleClick(MouseEvent e) {}

  @Override
  public void mouseDown(MouseEvent e) {}

  @Override
  public void mouseUp(MouseEvent e) {
    final Shell dialogue = UIUtils.createDialogue(parent, "Pull Request Info", 1);
    final Composite inputFields = UIUtils.createContainer(dialogue, 2);
    
    GridData gridData = new GridData();
    gridData.widthHint = 250;

    UIUtils.addLabel(inputFields, "Title of the Pull Request");
    Text title = UIUtils.addInputTextBox(inputFields, gridData, "", "Title of the pull request");
    UIUtils.addLabel(inputFields, "Submit this Branch");
    
    Text head;
    String currentBranch = "";
    try {
      currentBranch = this.gitManager.getCurrentBranch();
      currentBranch = currentBranch == null ? "" : currentBranch;
    } catch (Exception e2) {
      e2.printStackTrace();
    }
    finally {
      head = UIUtils.addInputTextBox(inputFields, gridData, currentBranch, "Branch to submit");
    }
    
    UIUtils.addLabel(inputFields, "Submit to this Branch");
    Text base = UIUtils.addInputTextBox(inputFields, gridData, "", "Branch to submit to");

    Label bodyLabel = UIUtils.addLabel(inputFields, "Body");
    GridData verticalAlign = new GridData();
    verticalAlign.verticalAlignment = SWT.BEGINNING;
    bodyLabel.setLayoutData(verticalAlign);
    
    Text body = new Text(inputFields, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
    gridData = new GridData(GridData.FILL_BOTH);
    gridData.heightHint = 250;
    body.setLayoutData(gridData);
    
    UIUtils.addSeparator(dialogue);
    
    Button confirmButton = new Button(dialogue, SWT.PUSH);
    confirmButton.setText("Confirm");
    confirmButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
    confirmButton.addMouseListener(new MouseListener() {

      @Override
      public void mouseDoubleClick(MouseEvent e) {
      }

      @Override
      public void mouseDown(MouseEvent e) {
      }

      @Override
      public void mouseUp(MouseEvent e) {
        PullRequest pullRequest = new PullRequest(title.getText(), head.getText(), base.getText(), body.getText());
        String result = "";
        try {
          OperationResult<PullRequestResultMsg> pullRequestResult = gitManager.createPullRequest(pullRequest);
          if (pullRequestResult.isSuccessful()) {
            result = "Pull Request is submitted successfully";
          } else {
            result = "Pull Request failed to be submitted\n";
            result += pullRequestResult.getDisplayableMessage();
          }
        } 
        catch (IOException e1) {
          e1.printStackTrace();
          result = e1.getMessage();
        } 
        finally {
          parent.updateOutputArea(result);
        }
        dialogue.close();
      }

    });
    
    dialogue.setFocus();
    dialogue.pack();
    UIUtils.centerShell(this.parent, dialogue);
    dialogue.open();   

  }
  

   

}
