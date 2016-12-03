package trellogitintegration.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;
/**
 * Unit Test
 * @author MC
 *
 */
public class CmdExecutorTest {

  private static final String INVALID_PROGRAM_TEMPLATE = "Cannot run program";
  private File currentDirectory = Paths.get("").toAbsolutePath().toFile();

  @Test
  public void sequentialExecutionSuccessTest()
      throws IOException, InterruptedException {
    CmdExecutionResult result = CmdExecutor.sequentialExecute("echo abc",
        this.currentDirectory);
    assertEquals("abc\n", result.getOutput());
  }

  @Test
  public void sequentialExecutionFailureTest() throws InterruptedException {
    CmdExecutionResult result = CmdExecutor.sequentialExecute("invalid program",
        this.currentDirectory);
    assertTrue(result.getException() instanceof CommandUnrecognizedException);
    assertTrue(
        result.getException().getMessage().contains(INVALID_PROGRAM_TEMPLATE));
    assertTrue(result.getOutput().isEmpty());
  }

  @Test
  public void sequentialGitTest() throws IOException, InterruptedException {
    CmdExecutionResult result = CmdExecutor.sequentialExecute("git --version",
        this.currentDirectory);
    assertTrue(result.getOutput().contains("git version"));
  }

}
