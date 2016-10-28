package trellogitintegration.persist.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import trellogitintegration.persist.IOUtils;

public class ConfigManagerTest {

  private File tempDirectory;
  
  @Before
  public void setup() throws IOException {
    this.tempDirectory = Files.createTempDirectory("temp").toFile();
    this.tempDirectory.mkdir();
  }
  
  @After
  public void close() {
    IOUtils.deleteFolder(this.tempDirectory);
    assertFalse(this.tempDirectory.exists());
  }
  
  @Test
  public void savingAndloadingConfigTest() throws IOException {
    final String projectName = "test project";
    ProjectConfig projectConfig = this.getMockedProjectConfig();
    ConfigManager manager = new ConfigManager(this.tempDirectory);
    
    manager.saveProjectConfig(projectName, projectConfig);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    
    ProjectConfig savedConfig = manager.loadProjectConfig(projectName);
    assertEquals(projectConfig, savedConfig);
  }
  
  @Test
  public void savingAndRemovingConfigTest() throws IOException {
    final String projectName = "test project";
    ProjectConfig projectConfig = this.getMockedProjectConfig();
    ConfigManager manager = new ConfigManager(this.tempDirectory);
    
    manager.saveProjectConfig(projectName, projectConfig);
    String configFileName = this.getConfigFileName(projectName);
    assertTrue(new File(this.tempDirectory, configFileName).exists());
    
    manager.deleteProjectConfig(projectName);
    assertFalse(new File(this.tempDirectory, configFileName).exists());
  }
  
  
  @Test
  public void savingAndListingConfigTest() throws IOException {
    final String projectName = "test project";
    ProjectConfig projectConfig = this.getMockedProjectConfig();
    ConfigManager manager = new ConfigManager(this.tempDirectory);
    
    manager.saveProjectConfig(projectName, projectConfig);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    
    String[] savedProjects = manager.getExitingProjects();
    assertTrue(savedProjects.length == 1);
    assertEquals(projectName, savedProjects[0]);
  }
  
  @Test
  public void loadingNonexistingProjectTest() throws JsonParseException, JsonMappingException, IOException {
    ConfigManager manager = new ConfigManager(this.tempDirectory);
    ProjectConfig config = manager.loadProjectConfig("inexisting project");
    assertEquals(new ProjectConfig(), config);
  }
  
  @Test
  public void updateExistingSavedConfigTest() throws IOException {
    String projectName = "test proejct";
    ProjectConfig config = this.getMockedProjectConfig();
    
    ConfigManager manager = new ConfigManager(this.tempDirectory);
    manager.saveProjectConfig(projectName, config);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    
    config.getTrelloConfig().setUrl("def.com");
    manager.saveProjectConfig(projectName, config);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    assertEquals(config, manager.loadProjectConfig(projectName));
  }
  
  @Test
  public void renamingProjectTest() throws IOException {
    String projectName = "test proejct";
    ProjectConfig config = this.getMockedProjectConfig();

    ConfigManager manager = new ConfigManager(this.tempDirectory);
    manager.saveProjectConfig(projectName, config);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    
    String newProjectName = "test project 2";
    assertTrue(manager.renameProjectConfigFile(projectName, newProjectName));
    assertFalse(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(newProjectName)).exists());
  }
  
  @Test
  public void renameNonexistingProjectTest() {
    String projectName = "test proejct";
    assertFalse(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    
    String newProjectName = "test project 2";
    ConfigManager manager = new ConfigManager(this.tempDirectory);
    assertFalse(manager.renameProjectConfigFile(projectName, newProjectName));
  }
  
  @Test
  public void renameToConflictingProjectNameTest() throws IOException {
    String projectName = "test proejct";
    ProjectConfig config = this.getMockedProjectConfig();

    ConfigManager manager = new ConfigManager(this.tempDirectory);
    manager.saveProjectConfig(projectName, config);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    
    String projectName2 = "test proejct 2";
    manager.saveProjectConfig(projectName2, config);
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName2)).exists());
    
    assertFalse(manager.renameProjectConfigFile(projectName, projectName2));
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName)).exists());
    assertTrue(new File(this.tempDirectory, this.getConfigFileName(projectName2)).exists());
    
  }
  
  
  private ProjectConfig getMockedProjectConfig() {
    final String url = "abc.com";
    final String board = "testing board";
    final String key = "23456789!@#$%^&*()";
    final String token = "@#$%^&*0987654SWERTYUJI";
    final ProjectConfig projectConfig = new ProjectConfig();
    ProjectConfig.TrelloConfig trelloConfig = projectConfig.new TrelloConfig(url, board, key, token);
    projectConfig.setTrelloConfig(trelloConfig);
   
   return projectConfig;
  }
    
  private String getConfigFileName(String projectName) {
    return String.format("%s %s.%s", "TG", projectName, "config");
  }
}
