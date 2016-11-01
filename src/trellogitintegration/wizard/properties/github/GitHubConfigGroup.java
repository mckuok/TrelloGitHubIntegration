package trellogitintegration.wizard.properties.github;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.persist.config.ProjectConfig.GitConfig;
import trellogitintegration.wizard.properties.WizardActions;
import trellogitintegration.wizard.properties.utils.GroupTemplate;
import trellogitintegration.wizard.properties.utils.UIUtils;

public class GitHubConfigGroup extends GroupTemplate implements WizardActions {

  private static final String TITLE = "GitHub Settings";
  
  private final GitConfig gitConfig;
  private final Text repoText;
  private final Text tokenText;
  private final GridData gridData;
  
  public GitHubConfigGroup(Composite parent, GridData gridData, GitConfig gitConfig) {
    super(parent, TITLE);
    this.gitConfig = gitConfig;
    this.gridData = gridData;
    
    UIUtils.addLabel(this, "Repository URL");
    this.repoText = UIUtils.addInputTextBox(this, this.gridData, this.gitConfig.getRepo());
    UIUtils.addLabel(this, "GitHub Token");
    this.tokenText = UIUtils.addInputTextBox(this, this.gridData, this.gitConfig.getToken());
  }

  @Override
  public void save() {
    this.gitConfig.setRepo(this.repoText.getText());
    this.gitConfig.setToken(this.tokenText.getText());    
  }

  @Override
  public void returnDefault() {
    this.repoText.setText(this.gitConfig.getRepo());
    this.tokenText.setText(this.gitConfig.getToken());
  }

  @Override
  public void apply() {
    this.save();    
  }
  

}
