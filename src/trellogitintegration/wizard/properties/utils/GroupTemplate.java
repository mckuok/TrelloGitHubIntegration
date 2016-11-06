 package trellogitintegration.wizard.properties.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import trellogitintegration.utils.ValidationUtils;

/**
 * A template for setting up a group for UI
 * 
 * Sample:
 * 
 * ---Title---------------------------------------
 * |                                             |
 * |                                             |
 * |_____________________________________________| 
 * 
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class GroupTemplate extends Group {

  /**
   * Create a group under the parent, with title and number of column specified
   * in the parameters
   * 
   * @param parent
   *          parent of this group
   * @param title
   *          Title of the group
   * @param columnNum
   *          number of columns in a grid layout
   */
  public GroupTemplate(Composite parent, String title, int columnNum) {
    super(parent, SWT.SHADOW_ETCHED_IN);
    ValidationUtils.checkNull(parent);
    ValidationUtils.checkNullOrEmpty(title);
    ValidationUtils.checkNegative(columnNum);

    GridLayout layout = new GridLayout();
    layout.numColumns = columnNum;
    this.setLayout(layout);

    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    this.setLayoutData(data);

    this.setText(title);
  }

  /**
   * Suppresses extension exception
   */
  public void checkSubclass() {
  }
 

}
