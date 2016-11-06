package trellogitintegration.views.github;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.exec.git.GitDisplayable;
import trellogitintegration.exec.git.GitOperation;
import trellogitintegration.utils.ValidationUtils;

/**
 * This class creates an input handler with radio buttons. Only one button can
 * be selected at a time
 * 
 * Created: Nov 6, 2016
 * 
 * @author Man Chon Kuok
 */
public class ButtonInputHandler extends InputHandler {

  private String[] options;
  private Button[] buttons;

  /**
   * Creates an input handler using radio buttons
   * 
   * @param operation
   *          operation for this handler
   * @param options
   *          options for the buttons
   */
  public ButtonInputHandler(GitOperation operation, String[] options) {
    super(operation, "");
    this.options = options;
    this.buttons = new Button[this.options.length];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addToGUI(Composite parent) {
    ValidationUtils.checkNull(parent);

    if (this.needsUserInput()) {
      UIUtils.addLabel(parent,
          GitDisplayable.getInstruction(this.getOperation()));
      Composite container = UIUtils.createContainer(parent, 3); 

      for (int i = 0; i < options.length; i++) {
        this.buttons[i] = new Button(container, SWT.RADIO);
        this.buttons[i].setText(options[i]);
      }

      for (int i = 0; i < buttons.length; i++) {
        final int current = i;
        this.buttons[i].addSelectionListener(new SelectionListener() {

          /**
           * {@inheritDoc} When one is selected, the others need to be
           * unselected
           */
          @Override
          public void widgetSelected(SelectionEvent e) {
            for (int i = 0; i < buttons.length; i++) {
              buttons[i].setSelection(false);
            }
            buttons[current].setSelection(true);
          }

          @Override
          public void widgetDefaultSelected(SelectionEvent e) {
          }

        });
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUserInput() {
    for (int i = 0; i < this.buttons.length; i++) {
      if (this.buttons[i].getSelection()) {
        return this.buttons[i].getText();
      }
    }
    return "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addModifyListener(CommandDisplayer display) {
    ValidationUtils.checkNull(display);
    
    for (int i = 0; i < buttons.length; i++) {
      this.buttons[i].addSelectionListener(new SelectionListener() {

        @Override
        public void widgetSelected(SelectionEvent e) {
          display.update();
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

      });
    }
  }
}
