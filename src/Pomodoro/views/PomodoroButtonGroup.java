package pomodoro.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pomodoro.exec.PomodoroExecutor;
import trellogitintegration.views.github.ButtonGroup;

public class PomodoroButtonGroup extends ButtonGroup{
	
	private final PomodoroExecutor pomodoroExecutor;
	
	private Button start;
	private Button pause;
	
	public PomodoroButtonGroup(PomodoroViewGroup parent, PomodoroExecutor pomodoroExecutor) {
		super(parent);
		this.pomodoroExecutor = pomodoroExecutor;
	}
	@Override
	protected void addButtonsToContainer(Composite container) {
		this.start = new Button(container, SWT.PUSH);
		this.start.setText("Start Timer");
		
		this.pause = new Button(container, SWT.PUSH);
		this.pause.setText("Pause");
	}
	
	@Override
	protected void addActionListeners() throws Exception {
		this.start.addMouseListener(new PomodoroStartListener(this.pomodoroExecutor));
		this.pause.addMouseListener(new PomodoroPauseListener(this.pomodoroExecutor));
	}
	
	@Override
	protected Button[] getButtons() {
		return new Button[] {this.start, this.pause};
	}
	
	
}
