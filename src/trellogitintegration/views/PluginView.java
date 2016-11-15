package trellogitintegration.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import pomodoro.PomodoroTimer;
import pomodoro.exec.PomodoroExecutor;
import pomodoro.views.PomodoroViewGroup;
import trellogitintegration.Activator;
import trellogitintegration.eclipse.utils.EclipseHelperUtils;
import trellogitintegration.exec.git.GitManager;
import trellogitintegration.persist.config.ConfigManager;
import trellogitintegration.persist.config.ProjectConfig.GitConfig;
import trellogitintegration.persist.config.ProjectConfig.PomodoroConfig;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.github.GitRepoViewGroup;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class PluginView extends ViewPart {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String ID = "trellogitintegration.views.PluginView";
  
  private final ConfigManager configManager = new ConfigManager(Activator.getDefault().getStateLocation().toFile());
  
  /**
   * The constructor.
   */
  public PluginView() {
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  public void createPartControl(Composite parent) {
    ValidationUtils.checkNull(parent);
    
    TabFolder folder = new TabFolder(parent, SWT.TOP);
    List<IProject> projects = EclipseHelperUtils.getOpenedProjects();
    Iterator<IProject> iterator = projects.iterator();
    while (iterator.hasNext()) {
      ProjectTab tab = new ProjectTab(folder, iterator.next());
      try {
        File projectDirectory = tab.getProject().getLocation().toFile();
        GitConfig gitConfig = this.configManager.loadProjectConfig(tab.getProject().getName()).getGitConfig();
        PomodoroConfig pomodoroConfig = this.configManager.loadProjectConfig(tab.getProject().getName()).getPomodoroConfig();
        new GitRepoViewGroup(tab.getContentArea(), new GitManager(projectDirectory, gitConfig));
        new PomodoroViewGroup(tab.getContentArea(), new PomodoroExecutor(new PomodoroTimer(pomodoroConfig)));
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus() {
  
  }
}
