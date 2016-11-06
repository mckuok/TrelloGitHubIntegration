package trellogitintegration.views.github;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.utils.ValidationUtils;

/**
 * This class creates a text field for displaying commands and its arguments
 * that are about to get executed.
 * 
 * Created: Nov 3, 2016
 * 
 * @author Man Chon Kuok
 */
public class CommandDisplayer {

  private final Composite parent;

  private Text displayArea;
  private List<InputHandler> inputHandlerList;

  /**
   * Create a display text field to display executing commands
   * 
   * @param parent
   *          parent for hosting the display
   * @param inputHandlerList
   *          list of input handlers that this displayer is watching 
   */
  public CommandDisplayer(Composite parent,
      List<InputHandler> inputHandlerList) {
    ValidationUtils.checkNull(parent, inputHandlerList);

    this.parent = parent;
    this.inputHandlerList = inputHandlerList;

  }

  /**
   * Update the argument for the command positioned at the given index
   */
  public void update() {
    this.displayArea.setText(this.reconstructOutput());
  }

  /**
   * construct the commands for execution based on the stored commands and the
   * argumentList
   * 
   * @return new execution commands
   */
  private String reconstructOutput() {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<InputHandler> inputIterator = this.inputHandlerList.iterator();

    while (inputIterator.hasNext()) {
      InputHandler input = inputIterator.next();
      stringBuilder.append(String.format(input.getOperation().getCommand(),
          input.getUserInput())).append("\n\n");
    }

    return stringBuilder.toString();
  }

  /**
   * Add the displayer to the GUI
   */
  public void addToGUI() {
    this.displayArea = new Text(this.parent,
        SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
    this.displayArea.setLayoutData(new GridData(GridData.FILL_BOTH));
    this.displayArea.setText(this.reconstructOutput());
  }

}
