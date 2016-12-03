package trellogitintegration.wizard.properties;

import java.io.IOException;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import pomodoro.views.PomodoroConfigGroup;
import trellogitintegration.Activator;
import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.persist.config.ConfigManager;
import trellogitintegration.persist.config.ProjectConfig;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.wizard.properties.github.GitHubConfigGroup;

/**
 * This class contains the configuration page for GitHub and Trello
 * configurations
 * 
 * Created: Nov 3, 2016
 * 
 * @author Man Chon Kuok
 */
public class GitHubTrelloPropertyPage extends PropertyPage {

  private static final int TEXT_FIELD_WIDTH = 50;

  private String projectName;
  private ConfigManager configManager = new ConfigManager(
      Activator.getDefault().getStateLocation().toFile());
  private ProjectConfig projectConfig;

  private GitHubConfigGroup gitHubGroup;
  private PomodoroConfigGroup pomodoroGroup;

  /**
   * Constructor for GitHubTrelloPropertyPage.
   */
  public GitHubTrelloPropertyPage() {
    super();
  }

  /**
   * @see PreferencePage#createContents(Composite)
   */
  protected Control createContents(Composite parent) {
    ValidationUtils.checkNull(parent);

    loadProjectConfig();
    Composite container = this.createContainer(parent);

    GridData gridData = new GridData();
    gridData.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);

    this.gitHubGroup = new GitHubConfigGroup(container, gridData,
        this.projectConfig.getGitConfig());
    UIUtils.addSeparator(container);
    this.pomodoroGroup = new PomodoroConfigGroup(container, gridData, this.projectConfig.getPomodoroConfig());

    return container;
  }

  /**
   * load the project configuration for the project that this property page is
   * responsible for
   */
  private void loadProjectConfig() {
    this.projectName = ((IJavaProject) super.getElement()).getProject()
        .getName();
    try {
      this.projectConfig = this.configManager
          .loadProjectConfig(this.projectName);
    } catch (IOException e) {
      this.projectConfig = new ProjectConfig();
    }
  }

  /**
   * Creates a container to contain groups
   * 
   * @param parent
   *          parent of the container
   * @return the container created
   */
  private Composite createContainer(Composite parent) {
    ValidationUtils.checkNull(parent);

    Composite composite = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    composite.setLayout(layout);
    GridData data = new GridData(GridData.FILL);
    data.grabExcessHorizontalSpace = true;
    composite.setLayoutData(data);

    return composite;
  }

  /**
   * Save configuration to the disk
   * 
   * @return true if saved ok, false otherwise
   */
  private boolean persistConfig() {
    try {
      this.configManager.saveProjectConfig(this.projectName,
          this.projectConfig);
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  /**
   * @inheritDoc
   */
  @Override
  protected void performDefaults() {
    super.performDefaults();
    this.gitHubGroup.returnDefault();
    this.pomodoroGroup.returnDefault();
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean performOk() {
    this.gitHubGroup.save();
    this.pomodoroGroup.save();
    
    return persistConfig();
  }

  /**
   * @inheritDoc
   */
  @Override
  public void performApply() {
    this.gitHubGroup.apply();
    this.pomodoroGroup.apply();

    persistConfig();
  }
}