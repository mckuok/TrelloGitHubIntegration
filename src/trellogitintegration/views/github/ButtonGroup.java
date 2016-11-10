package trellogitintegration.views.github;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import trellogitintegration.eclipse.utils.UIUtils;

/**
 * Button group that contains all the available operations for the given
 * project 
 * Created: Nov 10, 2016
 * @author Man Chon Kuok
 */
public abstract class ButtonGroup {

  private final GitRepoViewGroup viewGroup;
  private Composite container;
  private ButtonResizeController resizeController;
  
  /**
   * Creates a button group
   * @param viewGroup
   */
  public ButtonGroup(GitRepoViewGroup viewGroup) {
    this.viewGroup = viewGroup;
  }
  
  /**
   * Add the buttons to the container
   * @param container
   */
  protected abstract void addButtonsToContainer(Composite container);
 
  /**
   * Add action listeners for buttons 
   * @throws Exception
   */
  protected abstract void addActionListeners() throws Exception;
  
  /**
   * @return a list of buttons for the group
   */
  protected abstract Button[] getButtons();
  
  /**
   * Add the buttons to UI
   * @param container
   * @throws Exception
   */
  public void addToGUI(Composite container) throws Exception {  
    this.container = container;
    this.addButtonsToContainer(container);
    this.addActionListeners();
    this.resizeController =  new ButtonResizeController(); 
    
  }
  
  /**
   * @return project view group
   */
  public GitRepoViewGroup getViewGroup() {
    return this.viewGroup;
  }
  
  /**
   * Get back the resize controller, must be called after addToGUI
   * @return a ButtonResizeController
   */
  public ButtonResizeController getResizeController() {
    return this.resizeController;
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
