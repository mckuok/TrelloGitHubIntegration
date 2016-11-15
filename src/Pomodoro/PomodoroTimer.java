package pomodoro;

import trellogitintegration.persist.config.ProjectConfig.PomodoroConfig;

/**
 * This class creates a Pomodoro Timer
 * 
 * Created: Oct 22, 2016
 * @author Conner Higashino
 *
 */

public class PomodoroTimer {
	
	/**
	 * Start a pomodoro timer that repeats.
	 * 
	 * @param config The PomodoroConfig that will generate the times for the pomodoro
	 *
	 */
	public PomodoroTimer(PomodoroConfig config) {
		
		/* set values */
		this.setPomodoroTime(config.getPomodoroTime());
		this.setPomodoros(config.getPomodoroCount());
		this.setBreakTime(config.getBreakTime());
		this.setLongBreakTime(config.getLongBreakTime());
		this.setLongBreakFreq(config.getLongBreakFreq());
	}

	public long getPomodoroTime() {
		return pomodoroTime;
	}
	public void setPomodoroTime(long pomodoroTime) {
		this.pomodoroTime = pomodoroTime;
	}
	public int getPomodoros() {
		return pomodoros;
	}
	public void setPomodoros(int pomodoros) {
		this.pomodoros = pomodoros;
	}
	public long getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(long breakTime) {
		this.breakTime = breakTime;
	}
	public long getLongBreakTime() {
		return longBreakTime;
	}
	public void setLongBreakTime(long longBreakTime) {
		this.longBreakTime = longBreakTime;
	}
	public long getLongBreakFreq() {
		return longBreakFreq;
	}
	public void setLongBreakFreq(long longBreakFreq) {
		this.longBreakFreq = longBreakFreq;
	}

	/* private variables */
	private long pomodoroTime;
	private int pomodoros;
	private long breakTime;
	private long longBreakTime;
	private long longBreakFreq;
}