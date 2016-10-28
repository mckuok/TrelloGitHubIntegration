package trellogitintegration.persist.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import trellogitintegration.persist.IOUtils;
import trellogitintegration.rest.JsonStringConverter;

/**
 * This class manages Project configuration using the project name
 * 
 * Created: Oct 20, 2016
 * @author Man Chon Kuok
 */
public class ConfigManager {

  private static final String CONFIG_EXTENSION = ".config";
  private static final String PREFIX = "TG";

  private final File root;

  /**
   * Creates an instance of the manager
   * @param root The root of plugin folder where the manager is allowed to write to
   */
  public ConfigManager(File root) {
    this.root = root;
    if (!this.root.exists()) {
      this.root.mkdirs();
    }
  }

  /**
   * @return a list of existing projects that have their config saved
   */
  public String[] getExitingProjects() {
    String[] savedConfigFileNames = this.root.list(new FilenameFilter() {

      @Override
      public boolean accept(File dir, String filename) {
        return filename.startsWith(PREFIX)
            && filename.endsWith(CONFIG_EXTENSION);
      }

    });

    String[] projectNames = new String[savedConfigFileNames.length];
    for (int i = 0; i < savedConfigFileNames.length; i++) {
      projectNames[i] = 
          this.stripFileNameToProjectName(savedConfigFileNames[i]);
    }

    return projectNames;
  }

  /**
   * save / update the ProjectConfig for the given project
   * @param projectName name the project
   * @param config configuration of the project
   * @throws IOException thrown if problems occurred during the writing to the disk  
   */
  public void saveProjectConfig(String projectName, ProjectConfig config)
      throws IOException {
    File projectConfigFile = new File(this.root,
        getConfigFileName(projectName));
    if (!projectConfigFile.exists()) {
      projectConfigFile.createNewFile();
    }

    String json = JsonStringConverter.toString(config);
    FileOutputStream outputStream = new FileOutputStream(projectConfigFile);
    IOUtils.writeToStream(outputStream, json);
  }

  /**
   * load the project config using the given project name
   * @param projectName name of the project
   * @return the stored project configuration
   * @throws JsonParseException if parsing went wrong
   * @throws JsonMappingException if mapping went wrong (String -> member variables)
   * @throws IOException reading went wrong
   */
  public ProjectConfig loadProjectConfig(String projectName)
      throws JsonParseException, JsonMappingException, IOException {
    File projectConfigFile = new File(this.root,
        getConfigFileName(projectName));
    if (!projectConfigFile.exists()) {
      return new ProjectConfig();
    }

    FileInputStream inputStream = new FileInputStream(projectConfigFile);
    String content = IOUtils.readFromStream(inputStream);
    ProjectConfig projectConfig = JsonStringConverter.toObject(content, ProjectConfig.class);
    
    return projectConfig;
  }
  
  /**
   * Rename the existing project config file to a new one.   
   * @param oldProjectName the old project name. must exists 
   * @param newProjectName the new project name, should not conflict with other saved project's name
   * @return true if rename successfully, false otherwise
   */
  public boolean renameProjectConfigFile(String oldProjectName, String newProjectName) {
    File oldProjectFile = new File(this.root, this.getConfigFileName(oldProjectName));
    File newProjectFile = new File(this.root, this.getConfigFileName(newProjectName));
    
    if (newProjectFile.exists() || !oldProjectFile.exists()) {
      return false;
    } else {
      return oldProjectFile.renameTo(newProjectFile);
    }
  }

  /**
   * Remove the project configuration
   * @param projectName name of the project
   * @return true if deleted successfully, false otherwise
   */
  public boolean deleteProjectConfig(String projectName) {
    File projectConfigFile = new File(this.root,
        getConfigFileName(projectName));
    if (!projectConfigFile.exists()) {
      return true;
    } else {
      return projectConfigFile.delete();
    }
  }

  /**
   * Convert project name from projectName to "TG [projectName].config"
   * The file is given a prefix and an extension to prevent noise  
   * @param projectName name of the project
   * @return  "TG [projectName].config"
   */
  private String getConfigFileName(String projectName) {
    return String.format("%s %s%s", PREFIX, projectName, CONFIG_EXTENSION);
  }

  /**
   * strip the name of the file to project from "TG [projectName].config" to 
   * projectName
   * @param filename name of the file
   * @return the project name
   */
  private String stripFileNameToProjectName(String filename) {
    return filename.replaceFirst(PREFIX + " ", "").replace(CONFIG_EXTENSION,
        "");

  }
}
