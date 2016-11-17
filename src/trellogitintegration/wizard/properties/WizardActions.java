package trellogitintegration.wizard.properties;

/**
 * Interface to define methods needed for wizard page control
 * 
 * Created: Nov 3, 2016
 * 
 * @author Man Chon Kuok
 */
public interface WizardActions {

  /**
   * Return the options back to default
   */
  public void returnDefault();

  /**
   * Apply the current settings without closing the wizard page
   */
  public void apply();

  /**
   * Save the current settings
   */
  public void save();

}
