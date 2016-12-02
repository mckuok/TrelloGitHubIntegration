package pomodoro.views;

import org.eclipse.swt.widgets.Composite;

import pomodoro.exec.PomodoroExecutor;
import trellogitintegration.utils.ValidationUtils;
import trellogitintegration.views.github.ButtonGroup;
import trellogitintegration.wizard.properties.utils.GroupTemplate;

public class PomodoroViewGroup extends GroupTemplate {

	private final PomodoroExecutor pomodoroExecutor;

	public PomodoroViewGroup(Composite parent, PomodoroExecutor pomodoroExecutor) throws Exception {
		super(parent, "Pomodoro Timer", 1);
		ValidationUtils.checkNull(parent, pomodoroExecutor);
		this.pomodoroExecutor = pomodoroExecutor;

		ButtonGroup buttonGroup = null;
		buttonGroup = new PomodoroButtonGroup(this, this.pomodoroExecutor);
		buttonGroup.addToGUI(this);
		buttonGroup.addToGUI(parent);
		this.addControlListener(buttonGroup.getResizeController());
	}
}