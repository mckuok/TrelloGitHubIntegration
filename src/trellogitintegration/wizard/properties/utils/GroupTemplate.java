 package trellogitintegration.wizard.properties.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class GroupTemplate extends Group {

  public GroupTemplate(Composite parent, String title) {
    super(parent, SWT.SHADOW_ETCHED_IN);
    
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.setLayout(layout);
    
    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    this.setLayoutData(data);
    
    this.setText(title);
  }
  
  public void checkSubclass(){}
 

}
