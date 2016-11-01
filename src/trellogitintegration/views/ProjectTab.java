package trellogitintegration.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ProjectTab extends TabItem {

  private final IProject project;
  private final SashForm sashForm;
  
  public ProjectTab(TabFolder parent, IProject project) {
    super(parent, SWT.NONE);
    this.project = project;
    this.setText(this.project.getName());
    this.sashForm = new SashForm(parent, SWT.HORIZONTAL);
    this.setControl(this.sashForm);
  }
  
  public IProject getProject() {
    return this.project;
  }
  
  public SashForm getContentArea() {
    return this.sashForm;
  }
  
  
  public void checkSubclass(){}
  

}
