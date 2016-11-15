package pomodoro.views;


import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import pomodoro.exec.PomodoroExecutor;

public class PomodoroStartListener implements MouseListener{
	private PomodoroExecutor pomodoroExecutor;
	
	public PomodoroStartListener(PomodoroExecutor pomodoroExecutor) {
		this.pomodoroExecutor = pomodoroExecutor;
	}
	
	public void mouseDoubleClick(MouseEvent e) {
		
	}
	public void mouseDown(MouseEvent e) {
		
	}
	public void mouseUp(MouseEvent e) {
		try {
			Thread t = new Thread (pomodoroExecutor);
			t.start();
			System.out.println("Test");
		}
		catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.out.println("Rargh");
		}
	}
}
