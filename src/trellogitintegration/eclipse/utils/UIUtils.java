package trellogitintegration.eclipse.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.utils.ValidationUtils;

public class UIUtils {

  /**
   * Add a label to the parent
   * 
   * @param parent
   *          parent of the label
   * @param text
   *          default lable message
   */
  public static void addLabel(Composite parent, String text) {
    Label label = new Label(parent, SWT.NONE);
    label.setText(text);
  }

  /**
   * Add an input text box to the UI
   * 
   * @param parent
   *          parent of the text box
   * @param gridData
   *          gridData used to describe the layout of the textbox
   * @param defaultText
   *          default text for the textbox
   * @return
   */
  public static Text addInputTextBox(Composite parent, GridData gridData,
      String defaultText, String hintText) {
    Text textbox = new Text(parent, SWT.SEARCH | SWT.SINGLE | SWT.BORDER);
    textbox.setLayoutData(gridData);
    textbox.setText(defaultText);
    textbox.setMessage(hintText);
    return textbox;
  }

  /**
   * Add an horizontal separator to the UI
   * 
   * @param parent
   *          parent of the separator
   */
  public static void addSeparator(Composite parent) {
    Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    separator.setLayoutData(gridData);
  }

  /**
   * Uniform all buttons' sizes. The buttons must exist in the the UI (already
   * populated)
   * 
   * @param parent
   *          parent of the buttons
   * @param buttons
   *          buttons to be resized
   */
  public static void uniformButtonSize(Composite parent, Button... buttons) {
    parent.layout();
    int maxHeight = 0;
    int maxWidth = 0;
    for (int i = 0; i < buttons.length; i++) {
      Point size = buttons[i].getSize();
      if (size.x > maxWidth) {
        maxWidth = size.x;
      }
      if (size.y > maxHeight) {
        maxHeight = size.y;
      }
    }

    GridData gridData = new GridData();
    gridData.widthHint = maxWidth;
    gridData.heightHint = maxHeight;

    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setLayoutData(gridData);
    }
  }

  /**
   * Center a child on the parent
   * 
   * @param parent
   *          parent
   * @param child
   *          child
   */
  public static void centerShell(Composite parent, Composite child) {
    Monitor primary = parent.getDisplay().getPrimaryMonitor();
    Rectangle bounds = primary.getBounds();
    Rectangle rect = child.getBounds();

    int x = bounds.x + (bounds.width - rect.width) / 2;
    int y = bounds.y + (bounds.height - rect.height) / 2;

    child.setLocation(x, y);
  }

  /**
   * Creates a default container with grid layout
   * 
   * @param parent
   *          parent of the container
   * @param numOfColumns
   *          number of columns in the grid
   * @return a created container
   */
  public static Composite createContainer(Composite parent, int numOfColumns) {
    ValidationUtils.checkNull(parent);

    Composite container = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = numOfColumns;
    container.setLayout(layout);

    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    container.setLayoutData(data);

    return container;
  }
}
