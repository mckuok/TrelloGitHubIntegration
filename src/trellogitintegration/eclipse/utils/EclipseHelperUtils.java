package trellogitintegration.eclipse.utils;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class EclipseHelperUtils {
  
  public static List<IProject> getOpenedProjects() {
    List<IProject> openedProjects = new LinkedList<>();
    
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    IProject[] projects = workspaceRoot.getProjects();
    for(int i = 0; i < projects.length; i++) {
       IProject project = projects[i];
       if(project.isOpen() ) {
         openedProjects.add(project);
       }
    }
    
    return openedProjects;
  }

}
