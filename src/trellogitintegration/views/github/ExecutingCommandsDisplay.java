package trellogitintegration.views.github;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ExecutingCommandsDisplay extends Text {

  private List<String> commands = new ArrayList<>();
  private List<String> commandTemplates = new ArrayList<>();
  
  public ExecutingCommandsDisplay(Composite parent, List<String> commands) {
    super(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
    this.setLayoutData( new GridData( GridData.FILL_BOTH ));
    
    this.commands = commands;
    this.commandTemplates.addAll(commands);
    
    this.setText(this.reconstructOutput());
  }
  
  public void update(int index, String argument) {
    commands.set(index, String.format(this.commandTemplates.get(index), argument));
    this.setText(this.reconstructOutput());
  }
  
  public String reconstructOutput() {
    StringBuilder stringBuilder = new StringBuilder();
    commands.stream().forEach(command -> stringBuilder.append(command.replaceAll("%s", "")).append("\n").append("\n"));
    return stringBuilder.toString();
  }
  
  public void checkSubclass(){}

}
