package Pomodoro;

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
		 * Check if the seconds are proper and increment minutes if they are not. If anything is 
		 * negative, set to 0. If pomodoros or long_break_freq is negative, set to 1
		 */
		while (sec >= 60) {
			mins++;
			sec -= 60;
		}
		while (breakSec >= 60) {
			breakMin++;
			breakSec -= 60;
		}
		while (longBreakSec >= 60) {
			longBreakMin++;
			longBreakSec -= 60;
		}
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
			mins = 25;
		}
		if (breakMin == 0 && breakSec == 0) {
			breakMin = 5;
		}
		if (longBreakMin == 0 && longBreakSec == 0) {
			longBreakMin = 10;
		}
		
		/* set values */
		this.setMins(mins);
		this.setSec(sec);
		this.setPomodoros(pomodoros);
		this.setBreakMin(breakMin);
		this.setBreakSec(breakSec);
		this.setLongBreakMin(longBreakMin);
		this.setLongBreakSec(longBreakSec);
		this.setLongBreakFreq(longBreakFreq);
	}
	
	private PomodoroTimer() {}
	
	/* setters */
	
	public void setMins(int mins) {
		this.mins = mins;
	}
	
	public void setSec(int sec) {
		this.sec = sec;
	}
	
	public void setPomodoros(int pomodoros) {
		this.pomodoros = pomodoros;
	}
	
	public void setBreakMin(int breakMin) {
		this.breakMin = breakMin;
	}
	
	public void setBreakSec(int breakSec) {
		this.breakSec = breakSec;
	}
	
	public void setLongBreakMin(int longBreakMin) {
		this.longBreakMin = longBreakMin;
	}
	
	public void setLongBreakSec(int longBreakSec) {
		this.longBreakSec = longBreakSec;
	}
	
	public void setLongBreakFreq(int longBreakFreq) {
		this.longBreakFreq = longBreakFreq;
	}
	
	/* getters */
	
	public int getMins() {
		return this.mins;
	}
	
	public int getSec() {
		return this.sec;
	}
	
	public int getPomodoros() {
		return this.pomodoros;
	}

	public int getBreakMin() {
		return this.breakMin;
	}
	
	public int getBreakSec() {
		return this.breakSec;
	}
	
	public int getLongBreakMin() {
		return this.longBreakMin;
	}

	public int getLongBreakSec() {
		return this.longBreakSec;
	}
	
	public int getLongBreakFreq() {
		return this.longBreakFreq;
	}
	
	/* private variables */
	int mins;
	int sec;
	int pomodoros;
	int breakMin;
	int breakSec;
	int longBreakMin;
	int longBreakSec;
	int longBreakFreq;
}