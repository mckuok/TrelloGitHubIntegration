package trellogitintegration.wizard.properties;

import java.io.IOException;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import trellogitintegration.Activator;
import trellogitintegration.persist.config.ConfigManager;
import trellogitintegration.persist.config.ProjectConfig;
import trellogitintegration.wizard.properties.github.GitHubGroup;
import trellogitintegration.wizard.properties.utils.UIUtils;

public class GitHubTrelloPropertyPage extends PropertyPage {

	private static final int TEXT_FIELD_WIDTH = 50;

	private final GridData gridData = new GridData();
	

	private String projectName;
	private ConfigManager configManager = new ConfigManager(Activator.getDefault().getStateLocation().toFile());
	private ProjectConfig projectConfig;
	
	private GitHubGroup gitHubGroup;
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public GitHubTrelloPropertyPage() {	  
		super();
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
	  loadProjectConfig();
    Composite composite = this.getParentContainer(parent);   
		
    this.gitHubGroup = new GitHubGroup(composite, this.gridData, this.projectConfig.getGitConfig());
		UIUtils.addSeparator(composite);
		
		return composite;
	}

	
	private void loadProjectConfig() {
	  this.projectName = ((IJavaProject) super.getElement()).getProject().getName();
    try {
      this.projectConfig = this.configManager.loadProjectConfig(this.projectName);
    } catch (IOException e) {
      this.projectConfig = new ProjectConfig();
    }
	}
	
	private Composite getParentContainer(Composite parent) {
	  this.gridData.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
	  
    Composite composite = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    composite.setLayout(layout);
    GridData data = new GridData(GridData.FILL);
    data.grabExcessHorizontalSpace = true;
    composite.setLayoutData(data);
    
    return composite;
	}
	
	private boolean persistConfig() {
	   try {
	      this.configManager.saveProjectConfig(this.projectName, this.projectConfig);
	    } catch (IOException e) {
	      return false;
	    }
	    return true;
	}
	
	
	protected void performDefaults() {
		super.performDefaults();
		this.gitHubGroup.returnDefault();
	}
	
	public boolean performOk() {
	  this.gitHubGroup.save();
	  
	  return persistConfig();
	}

	public void performApply() {
	  this.gitHubGroup.apply();
	  
	  persistConfig();
	}
}