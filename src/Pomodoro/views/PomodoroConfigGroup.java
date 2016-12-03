package Pomodoro.views;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import trellogitintegration.eclipse.utils.UIUtils;
import trellogitintegration.persist.config.ProjectConfig.PomodoroConfig;
import trellogitintegration.wizard.properties.WizardActions;
import trellogitintegration.wizard.properties.utils.GroupTemplate;

public class PomodoroConfigGroup extends GroupTemplate implements WizardActions {

	
	private final PomodoroConfig config;
	private final Text timerText;
	private final Text pomodoroText;
	private final Text breakText;
	private final Text longText;
	private final Text freqText;
	
	public PomodoroConfigGroup(Composite parent, GridData gridData, PomodoroConfig config) {
		super(parent, "Pomodoro Configuration", 1);
		this.config = config;
		UIUtils.addLabel(this, "How many Pomodoros do you want?");
		this.pomodoroText = UIUtils.addInputTextBox(this, gridData, Integer.toString(this.config.getPomodoroCount()), "");
		UIUtils.addLabel(this, "How long (in seconds) should a Pomodoro last?");
		this.timerText = UIUtils.addInputTextBox(this, gridData, Long.toString(this.config.getPomodoroTime()), "");
		UIUtils.addLabel(this, "How long (in seconds) should a break last?");
		this.breakText = UIUtils.addInputTextBox(this, gridData, Long.toString(this.config.getBreakTime()), "");
		UIUtils.addLabel(this, "How long (in seconds) should a long break last?");
		this.longText = UIUtils.addInputTextBox(this, gridData, Long.toString(this.config.getLongBreakTime()),  "");
		UIUtils.addLabel(this, "How frequently should a long break occur?");
		this.freqText = UIUtils.addInputTextBox(this, gridData, Integer.toString(this.config.getLongBreakFreq()), "");
	}
	
	@Override
	public void returnDefault() {
		this.config.reset();
		this.pomodoroText.setText(String.valueOf(this.config.getPomodoroCount()));
		this.timerText.setText(String.valueOf(this.config.getPomodoroTime()));
		this.breakText.setText(String.valueOf(this.config.getBreakTime()));
		this.longText.setText(String.valueOf(this.config.getLongBreakTime()));
		this.freqText.setText(String.valueOf(this.config.getLongBreakFreq()));
	}

	@Override
	public void apply() {
		this.save();
		
	}

	@Override
	public void save() {
		this.config.setPomodoroCount(Integer.parseInt(this.pomodoroText.getText()));
		this.config.setPomodoroTime(Long.parseLong(this.timerText.getText()));
		this.config.setBreakTime(Long.parseLong(this.breakText.getText()));
		this.config.setLongBreakTime(Long.parseLong(this.longText.getText()));
		this.config.setLongBreakFreq(Integer.parseInt(this.freqText.getText()));
	}
	
}
