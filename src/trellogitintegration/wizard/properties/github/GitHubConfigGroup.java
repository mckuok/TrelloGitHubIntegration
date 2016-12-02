package trellogitintegration.wizard.properties.github;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.persist.config.ProjectConfig.GitConfig;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.wizard.properties.WizardActions;
import trellogitintegration.wizard.properties.utils.GroupTemplate;

/**
 * This class creates a Group for the Git Configuration UI on the property page
 * Created: Nov 3, 2016
 * 
 * @author Man Chon Kuok
 */
public class GitHubConfigGroup extends GroupTemplate implements WizardActions {

  private static final String TITLE = "GitHub Settings";
  private static final String REPO_URL = "Repository URL";
  private static final String GITHUB_TOKEN = "GitHub Token";

  private final GitConfig gitConfig;
  private final Text repoText;
  private final Text tokenText;
  private final GridData gridData;

  /**
   * Create a GitHub group for the property page to store configurations
   * 
   * @param parent
   *          parent of the group
   * @param gridData
   *          grid data used to define the layout of the group
   * @param gitConfig
   *          Pre stored git configurations
   */
  public GitHubConfigGroup(Composite parent, GridData gridData,
      GitConfig gitConfig) {
    super(parent, TITLE, 2);
    ValidationUtils.checkNull(parent, gridData, gitConfig);

    this.gitConfig = gitConfig;
    this.gridData = gridData;

    UIUtils.addLabel(this, REPO_URL);
    String url = "https://github.com/" + this.gitConfig.getUsername() + "/" + this.gitConfig.getRepo();
    this.repoText = UIUtils.addInputTextBox(this, this.gridData,
        url, "Repo URL ex) https://github.com/mckuok/gitpractice");
    UIUtils.addLabel(this, GITHUB_TOKEN);
    this.tokenText = UIUtils.addInputTextBox(this, this.gridData,
        this.gitConfig.getToken(), "Token from the GitHub Settings");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save() {
    String url = this.repoText.getText();
    String[] usernameRepoPair = url.replace("https://github.com/", "").replace("http://github.com/", "").replace("github.com/", "").split("/");
    this.gitConfig.setUsername(usernameRepoPair[0]);
    this.gitConfig.setRepo(usernameRepoPair[1]);
    this.gitConfig.setToken(this.tokenText.getText());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void returnDefault() {
    this.repoText.setText(this.gitConfig.getRepo());
    this.tokenText.setText(this.gitConfig.getToken());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply() {
    this.save();
  }

}
