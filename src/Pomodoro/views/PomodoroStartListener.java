package Pomodoro.views;


import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import Pomodoro.exec.PomodoroExecutor;

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
			//System.out.println("Test");
			Frame f = new Frame();
			f.setSize(200,200);
			JDialog jd = new JDialog(f, "Start", false);
			jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			jd.setAlwaysOnTop(true);
			jd.setLocation(200, 200);
			jd.setLocationRelativeTo(null);
			jd.setSize(200, 200);
			JLabel startText = new JLabel("Timer has started", JLabel.CENTER);
			jd.add(startText);
			jd.setVisible(true);
		}
		catch (Exception exception) {
			System.err.println(exception.getMessage());
			System.out.println("Rargh");
		}
	}
}
