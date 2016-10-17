package trellogitintegration.exec.git;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitManagerTest {

  private static final String TEST_FILE = "test.txt";
  
  private File tempDirectory;
  private GitManager gitManager;
  
  @Before
  public void setup() throws Exception {
    this.tempDirectory = Files.createTempDirectory("temp").toFile();
    this.tempDirectory.mkdir();
    this.gitManager = new GitManager(this.tempDirectory);
    assertTrue(this.gitManager.init());
  }
  
  @After
  public void close() {
    deleteFolder(this.tempDirectory);
  }
  
  @Test
  public void statusTest() throws Exception {
    assertTrue(this.gitManager.status());
  }
  
  @Test
  public void addTest() throws Exception {
    createFile();
    assertTrue(this.gitManager.addAll());
    assertFalse(this.gitManager.add("randomName.txt"));
    assertTrue(this.gitManager.status());
  }
  
  @Test
  public void commitTest() throws Exception {
    createFile();
    assertTrue(this.gitManager.addAll());
    assertTrue(this.gitManager.commit("test"));
  }
  
  @Test
  public void branchTest() throws Exception {
    String testBranch1 = "testBranch1";
    String testBranch2 = "testBranch2";
    assertTrue(this.gitManager.newBranch(testBranch1));
    createFile();
    assertTrue(this.gitManager.newBranch(testBranch2));
    assertTrue(this.gitManager.add(TEST_FILE));    
  }
  
  
  private void createFile() throws IOException {
    File newFile = new File(this.tempDirectory, TEST_FILE);
    newFile.createNewFile();
  }
  
  private void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if(files != null) { 
        for(File f: files) {
            if(f.isDirectory()) {
                deleteFolder(f);
            } else {
                f.delete();
            }
        }
    }
    folder.delete();
}
}
