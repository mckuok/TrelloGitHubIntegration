package pomodoro.exec;

import static org.junit.Assert.*;

import org.junit.Test;
import Pomodoro.PomodoroTimer;
import Pomodoro.exec.PomodoroExecutor;
import trellogitintegration.persist.config.ProjectConfig.PomodoroConfig;

public class PomodoroExecutorTest {

  private PomodoroConfig config;
  private PomodoroTimer time;
  private PomodoroExecutor exec;
  
	@Test
	public void test() {
		fail("Not yet implemented");
	}

  @Test
	public void testCounts(){
	  time = new PomodoroTimer(config);
	  time.refreshPomodoroTimer();
	  assertEquals(0, time.getPomodoroTime());
	  assertEquals(0, time.getPomodoros());
	  assertEquals(5, time.getBreakTime());
	  assertEquals(10, time.getLongBreakTime());
	}
  
}
