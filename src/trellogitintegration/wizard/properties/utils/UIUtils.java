package trellogitintegration.wizard.properties.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class UIUtils {

  public static void addLabel(Composite parent, String text) {
    Label label = new Label(parent, SWT.NONE);
    label.setText(text);
  }
  
  public static Text addInputTextBox(Composite parent, GridData gridData, String defaultText) {
    Text textbox = new Text(parent, SWT.SINGLE | SWT.BORDER);
    textbox.setLayoutData(gridData);
    textbox.setText(defaultText);
    return textbox;
  }
  
  public static void addSeparator(Composite parent) {
    Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    separator.setLayoutData(gridData);
  }  
}
