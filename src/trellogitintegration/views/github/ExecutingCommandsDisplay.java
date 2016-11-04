package trellogitintegration.views.github;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.utils.ValidationUtils;

/**
 * This class creates a text field for displaying commands and its arguments that 
 * are about to get executed.
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class ExecutingCommandsDisplay extends Text {

  private List<String> commands = new ArrayList<>();
  private List<String> commandTemplates = new ArrayList<>();
  private List<String> argumentList = new ArrayList<>();
  
  /**
   * Create a display text field to display executing commands 
   * @param parent parent for hosting the display
   * @param commands commands to be executed 
   * @param argumentList a list of arguments for the commands
   */
  public ExecutingCommandsDisplay(Composite parent, List<String> commands, List<String> argumentList) {
    super(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
    ValidationUtils.checkNull(parent, commands, argumentList);
    if (commands.size() != argumentList.size()) {
      throw new IllegalArgumentException("command size should be the same as the argument list size");
    }
    
    this.setLayoutData( new GridData( GridData.FILL_BOTH ));
    
    this.commands = commands;
    this.commandTemplates.addAll(commands);
    this.argumentList.addAll(argumentList);
    
    this.setText(this.reconstructOutput());
  }
  
  /**
   * Update the argument for the command positioned at the given index
   * @param index index of the command
   * @param argument argument to be substitute in the command
   */
  public void update(int index, String argument) {
    commands.set(index, String.format(this.commandTemplates.get(index), argument));
    this.setText(this.reconstructOutput());
  }
  
  /**
   * construct the commands for execution based on the stored commands and the argumentList
   * @return new execution commands
   */
  private String reconstructOutput() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < this.commands.size(); i++) {
      stringBuilder.append(commands.get(i).replaceAll("%s", argumentList.get(i))).append("\n").append("\n");
    }
    return stringBuilder.toString();
  }
  
  /**
   * Suppresses extension exception 
   */
  public void checkSubclass(){}

}
