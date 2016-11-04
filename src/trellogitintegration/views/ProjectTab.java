package trellogitintegration.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import trellogitintegration.utils.ValidationUtils;

/**
 * This class creates a tab for a project
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class ProjectTab extends TabItem {

  private final IProject project;
  private final SashForm sashForm;
  
  /**
   * Creates a tab for a project
   * @param parent TabFolder that contains this tab
   * @param project project this tab is responsible for
   */
  public ProjectTab(TabFolder parent, IProject project) {
    super(parent, SWT.NONE);
    ValidationUtils.checkNull(parent, project);
    
    this.project = project;
    this.setText(this.project.getName());
    this.sashForm = new SashForm(parent, SWT.HORIZONTAL);
    this.setControl(this.sashForm);
  }
  
  /**
   * @return the project associated with this tab
   */
  public IProject getProject() {
    return this.project;
  }
  
  /**
   * Get the content area of where the content can be displayed
   * @return the content area
   */
  public SashForm getContentArea() {
    return this.sashForm;
  }
  
  /**
   * Suppresses extension exception
   */
  public void checkSubclass(){}
  

}
