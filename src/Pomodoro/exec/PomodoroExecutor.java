package pomodoro.exec;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import pomodoro.PomodoroTimer;

/** 
	* This class starts the Pomodoro countdown based on the input
	* 
	* 
	* Created: Oct 24, 2016
  * @author Conner Higashino
  */

public class PomodoroExecutor implements Runnable{
	
	private PomodoroTimer timer;
	/* times are all in seconds */
	private long pomodoroTimeSet;									//length of time for each pomodoro
	private long breakTimeSet;								//length of time for each normal break
	private long longTimeSet;									//length of time for each long break
	private long countdownTime;						//time remaining in current countdown
	
	private boolean pause;
	
	private int currentPomodoro;					//what number pomodoro is this, starts at 1
	
	public enum currentTimer {						//indicate which timer is counting down
		POMODORO, BREAK, LONGBREAK, FINISHED
	};
	currentTimer currentCountdown;
	
	/* constructors */
	
	public PomodoroExecutor (PomodoroTimer timer) {
		this.setTimer(timer);
		this.initTimer();
	}
	
	/* Executer functions */
	
	/**
	 * This function initializes all timer fields and booleans, setting the current timer to the Pomodoro work time
	 */
	public void initTimer() {
		/* set normal timer */
		this.setPomodoroTimeRemaining(this.getTimer().getPomodoroTime());
		/* set break timer */
		this.setBreakTimeRemaining(this.getTimer().getBreakTime());
		/* set long break timer */
		this.setLongBreakTimeRemaining(this.getTimer().getLongBreakTime());
		this.currentCountdown = currentTimer.POMODORO;
		this.setCountdown();
		this.resetCurrentPomodoro();
		this.pause = false;
		System.out.println("Initializing.  .  .");
	}
		
	/**
	 * Initialize a countdown timer based on which is the active timer
	 * @throws UnsupportedOperationException 
	 */
	public void setCountdown() throws UnsupportedOperationException {
		switch (currentCountdown) {
		case POMODORO:
			this.countdownTime = this.getPomodoroTime();
			break;
		case BREAK:
			this.countdownTime = this.getBreakTime();
			break;
		case LONGBREAK:
			this.countdownTime = this.getLongBreakTime();
			break;
		case FINISHED:
			this.isFinished();
			break;
		default:
			throw new UnsupportedOperationException("Couldn't pick current timer\n");
		}
	}
	
	/**
	 * Starts counting down, decrements currentTime every second(with some drift). At the end of the
	 *   countdown, sets the time for the next appropriate countdown. Continues automatically until the final pomodoro ends
	 * @throws Exception 
	 */
	
	public void run() {
		try {
			this.initTimer();
			this.countdown();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void countdown() throws Exception {
		while (this.getCurrentPomodoro() <= this.getTimer().getPomodoros()) {
			while (this.getCountdownTime() != 0 && !this.pause) {
				this.delay_sec();
				this.setCountdownTime(this.getCountdownTime() - 1);
			}
			switch (currentCountdown) {
				case POMODORO:
					if (this.getCurrentPomodoro() == this.getTimer().getPomodoros()) {
						/* if last pomodoro ended */
						this.currentCountdown = currentTimer.FINISHED;
					} else if ((this.getCurrentPomodoro() % this.getTimer().getLongBreakFreq()) == 0) {
						/* if it's time for a long break */
						this.currentCountdown = currentTimer.LONGBREAK;
					} else {
						/* if it's time for a normal break */
						this.currentCountdown = currentTimer.BREAK;
					}
					this.incrementCurrentPomodoro();
					break;
				case BREAK:
					this.currentCountdown = currentTimer.POMODORO;
					break;
				case LONGBREAK:
					this.currentCountdown = currentTimer.POMODORO;
					break;
				case FINISHED:
					this.isFinished();
				default:
					throw new Exception("Couldn't change current timer type\n");
			}
			/* reset the countdown to the correct time */
			this.setCountdown();
		}
	}

	public void delay_sec() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			this.pause();
		}
	}
	
	/* mutators */
	
	public void incrementCurrentPomodoro() {
		this.currentPomodoro++;
	}
	
	public void resetCurrentPomodoro() {
		this.currentPomodoro = 1;
	}
	
	/* pause */
	
	public void pause() {
		this.pause = true;
	}
	
	public void unpause() {
		this.pause = false;
	}
	
	/* setters */
	
	public void setTimer(PomodoroTimer timer) {
		this.timer = timer;
	}
	
	public void setPomodoroTimeRemaining(long time) {
		this.pomodoroTimeSet = time;
	}
	
	public void setBreakTimeRemaining(long time) {
		this.breakTimeSet = time;
	}
	
	public void setLongBreakTimeRemaining(long time) {
		this.longTimeSet = time;
	}

	public void setCountdownTime(long time) {
		this.countdownTime = time;
	}
	
	
	/* getters */
	
	public PomodoroTimer getTimer() {
		return this.timer;
	}
	
	public long getPomodoroTime() {
		return this.pomodoroTimeSet;
	}
	
	public long getBreakTime() {
		return this.breakTimeSet;
	}
	
	public long getLongBreakTime() {
		return this.longTimeSet;
	}
	
	public long getCountdownTime() {
		return this.countdownTime;
	}
	
	public int getCurrentPomodoro() { 
		return this.currentPomodoro;
	}
	
	public void isFinished() {
		Frame f = new Frame();
		JDialog jd = new JDialog(f, "Finished", false);
		f.setSize(200, 200);
		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jd.setSize(200,200);
		jd.setLocation(200,200);
		jd.setLocationRelativeTo(null);
		JLabel finishText = new JLabel("All pomodoros have finished", JLabel.CENTER);
		jd.add(finishText);
		jd.setVisible(true);
	}
}
