package pomodoro;

/**
 * This class creates a Pomodoro Timer
 * 
 * Created: Oct 22, 2016
 * @author Conner Higashino
 *
 */

public class PomodoroTimer {
	
	/**
	 * Start a pomodoro timer that repeats. If any sec >= 60, minute will be incremented until 
	 * sec < 60
	 * 
	 * TODO: Set restrictions for setters
	 * 
	 * @param mins the number of minutes per pomodoro
	 * @param sec value 0-59, number of seconds per pomodoro
	 * @param pomodoros number of pomodoros
	 * @param breakMin number of minutes per break
	 * @param breakSec value 0-59, number of seconds per break
	 * @param longBreakMin number of minutes per long break
	 * @param longBreakSec value 0-59 number of minutes per long break
	 * @param longBreakFreq how many breaks between long breaks (ex. value of 3 means 2 normal 
	 *     breaks and the 3rd is a long break)
	 */
	public PomodoroTimer(int mins, int sec, int pomodoros, int breakMin, 
			int breakSec, int longBreakMin, int longBreakSec, int longBreakFreq) {
		/* 
		 * If anything is negative, set to 0. If pomodoros or long_break_freq is negative, set to 1
		 */
		if (mins < 0) {
			mins = 0;
		}
		if (sec < 0) {
			sec = 0;
		}
		if (pomodoros < 0) {
			pomodoros = 1;
		}
		if (breakMin < 0) {
			breakMin = 0;
		}
		if (breakSec < 0) {
			breakSec = 0;
		}
		if (longBreakMin < 0) {
			longBreakMin = 0;
		}
		if (longBreakSec < 0) {
			longBreakSec = 0;
		}
		if (longBreakFreq < 0) {
			longBreakFreq = 1;
		}
		
		/* check that every timer has minutes or seconds. If not, force values */
		
		if (mins == 0 && sec == 0) {
			sec = 1500;			//25 minutes
		}
		if (breakMin == 0 && breakSec == 0) {
			breakSec = 300;	//5 minutes
		}
		if (longBreakMin == 0 && longBreakSec == 0) {
			longBreakSec = 600;	//10 minutes
		}
		
		/* set values */
		this.setPomodoroTime(sec);
		this.setPomodoros(pomodoros);
		this.setBreakTime(breakSec);
		this.setLongBreakTime(longBreakSec);
		this.setLongBreakFreq(longBreakFreq);
	}
	
	private PomodoroTimer() {}
	
	/* setters */
	
	public void setPomodoroTime(int sec) {
		this.pomodoroTime = sec;
	}
	
	public void setPomodoros(int pomodoros) {
		this.pomodoros = pomodoros;
	}
	
	public void setBreakTime(int breakSec) {
		this.breakTime = breakSec;
	}
	
	public void setLongBreakTime(int longBreakSec) {
		this.longBreakTime = longBreakSec;
	}
	
	public void setLongBreakFreq(int longBreakFreq) {
		this.longBreakFreq = longBreakFreq;
	}
	
	/* getters */
	
	public int getPomodoroTime() {
		return this.pomodoroTime;
	}
	
	public int getPomodoros() {
		return this.pomodoros;
	}

	public int getBreakTime() {
		return this.breakTime;
	}
	
	public int getLongBreakTime() {
		return this.longBreakTime;
	}
	
	public int getLongBreakFreq() {
		return this.longBreakFreq;
	}
	
	/* private variables */
	int pomodoroTime;
	int pomodoros;
	int breakTime;
	int longBreakTime;
	int longBreakFreq;
}