package trellogitintegration.exec.git;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import trellogitintegration.exec.OperationResult;
import trellogitintegration.exec.git.github.pullrequest.PullRequestSuccessMsg;
import trellogitintegration.persist.IOUtils;
import trellogitintegration.rest.JsonStringConverter;

public class GitManagerTest {

  private static final String TEST_FILE = "test.txt";
  
  private File tempDirectory;
  private GitManager gitManager;
  
  @Before
  public void setup() throws Exception {
    this.tempDirectory = Files.createTempDirectory("temp").toFile();
    this.tempDirectory.mkdir();
    this.gitManager = new GitManager(this.tempDirectory, null);
    assertTrue(this.gitManager.init().isSuccessful());
    assertTrue(new File(this.tempDirectory, ".git").exists());
  }
  
  @After
  public void close() {
    IOUtils.deleteFolder(this.tempDirectory);
    assertFalse(this.tempDirectory.exists());
    this.gitManager = null;
  }
  
  @Test
  public void statusTest() throws Exception {
    OperationResult result = this.gitManager.status();
    assertTrue(result.isSuccessful());
    assertFalse(result.getMessage().isEmpty());
  }
  
  @Test
  public void addTest() throws Exception {
    createFile();
    assertTrue(this.gitManager.addAll().isSuccessful());
    assertFalse(this.gitManager.add("randomName.txt").isSuccessful());
    OperationResult result = this.gitManager.status();
    assertTrue(result.isSuccessful());
    assertFalse(result.getMessage().isEmpty());
  }
  
  @Test
  public void commitTest() throws Exception {
    createFile();
    assertTrue(this.gitManager.addAll().isSuccessful());
    assertTrue(this.gitManager.commit("test").isSuccessful());
  }
  
  @Test
  public void branchTest() throws Exception {
    String testBranch1 = "testBranch1";
    String testBranch2 = "testBranch2";
    assertTrue(this.gitManager.newBranch(testBranch1).isSuccessful());
    createFile();
    assertTrue(this.gitManager.newBranch(testBranch2).isSuccessful());
    assertTrue(this.gitManager.add(TEST_FILE).isSuccessful());    
    this.gitManager.getAllbranches();
  }
  
  @Test
  public void cloneTest() throws Exception {
    final String url = "https://github.com/mckuok/gitpractice";
    
    assertTrue(this.gitManager.clone(url).isSuccessful());
    assertTrue(this.gitManager.getWorkingDirectory().exists());
    assertTrue(new File(this.gitManager.getWorkingDirectory(), "README.md").exists());
  }
  
  @Test
  public void logTest() throws Exception {
    final String url = "https://github.com/mckuok/gitpractice";
    
    assertTrue(this.gitManager.clone(url).isSuccessful());
    assertTrue(this.gitManager.getWorkingDirectory().exists());
    assertTrue(new File(this.gitManager.getWorkingDirectory(), "README.md").exists());
    OperationResult result = this.gitManager.log();
    assertTrue(result.isSuccessful());
    assertFalse(result.getMessage().isEmpty());
  }
  
  private void createFile() throws IOException {
    File newFile = new File(this.tempDirectory, TEST_FILE);
    newFile.createNewFile();
  }

}
