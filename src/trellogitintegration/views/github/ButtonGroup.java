package trellogitintegration.views.github;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pomodoro.views.PomodoroViewGroup;
import trellogitintegration.eclipse.utils.UIUtils;

public abstract class ButtonGroup {

  private final Composite container;
  private final GitRepoViewGroup parent;
  private final PomodoroViewGroup pomodoroParent;
  
  public ButtonGroup(GitRepoViewGroup parent) {
    this.container = UIUtils.createContainer(parent, 1);
    this.parent = parent;
    this.pomodoroParent = null;
  }
  public ButtonGroup(PomodoroViewGroup pomodoroParent) {
  	this.container = UIUtils.createContainer(pomodoroParent, 1);
  	this.pomodoroParent = pomodoroParent;
  	this.parent = null;
  }
  
  protected abstract void addButtonsToContainer(Composite container);
  
  protected abstract void addActionListeners() throws Exception;
  
  protected abstract Button[] getButtons();
  
  public void addToGUI() throws Exception {  
    this.addButtonsToContainer(this.container);
    this.addActionListeners();
    
  
  }
  
  public GitRepoViewGroup getViewGroup() {
    return this.parent;
  }
  
  public PomodoroViewGroup getPomodoroViewGroup() {
  	return this.pomodoroParent;
  }
  
  public ButtonResizeController getResizeController() {
    return new ButtonResizeController();
  }
  
  /**
   * Listener to listener for paint and resize to make all buttons' sizes
   * uniform Created: Nov 3, 2016
   * 
   * @author Man Chon Kuok
   */
  private class ButtonResizeController
      implements PaintListener, ControlListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlMoved(ControlEvent e) {
      uniformButtonSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlResized(ControlEvent e) {
      uniformButtonSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintControl(PaintEvent e) {
      uniformButtonSize();
    }

    /**
     * Uniform buttons' sizes
     */
    private void uniformButtonSize() {
      UIUtils.uniformButtonSize(container, getButtons());
    }
  }

}
