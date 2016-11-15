package pomodoro.views;


import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import pomodoro.exec.PomodoroExecutor;

public class PomodoroPauseListener implements MouseListener{
	private PomodoroExecutor pomodoroExecutor;
	
	public PomodoroPauseListener(PomodoroExecutor pomodoroExecutor) {
		this.pomodoroExecutor = pomodoroExecutor;
	}
	
	public void mouseDoubleClick(MouseEvent e) {
		
	}
	public void mouseDown(MouseEvent e) {
		
	}
	public void mouseUp(MouseEvent e) {
		try {
			this.pomodoroExecutor.pause();
			System.out.println("Test1");
		}
		catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.out.println("Rargh");
		}
	}
}
