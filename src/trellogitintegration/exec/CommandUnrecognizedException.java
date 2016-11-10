package trellogitintegration.exec;

/**
 * Exception made specifically to capture exception thrown when command line
 * command is unrecognized
 * Created: Oct 16, 2016
 * @author Man Chon Kuok
 */
public class CommandUnrecognizedException extends Exception {
  
  private static final long serialVersionUID = -4194868372383461765L;
  public static final String INVALID_PROGRAM_TEMPLATE = "Cannot run program";
  
  public CommandUnrecognizedException(String message) {
    super(message);
  }
}