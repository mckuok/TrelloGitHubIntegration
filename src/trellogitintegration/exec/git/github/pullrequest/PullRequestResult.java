package trellogitintegration.exec.git.github.pullrequest;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import trellogitintegration.exec.OperationResult;
import trellogitintegration.rest.JsonStringConverter;

public class PullRequestResult extends OperationResult<PullRequestResultMsg> {

  private static final String FAILURE1 = "Failed";
  private static final String FAILURE2 = "Bad ";
  private final PullRequestResultMsg output;
  
  public PullRequestResult(String pullRequestReply) throws JsonParseException, JsonMappingException, IOException {
    super(!(pullRequestReply.contains(FAILURE1) || pullRequestReply.contains(FAILURE2)));
    if (super.isSuccessful()) {
      this.output = JsonStringConverter.toObject(pullRequestReply, PullRequestSuccessMsg.class);
    } else {
      this.output = JsonStringConverter.toObject(pullRequestReply, PullRequestErrorMsg.class);
    }
  }

  @Override
  public PullRequestResultMsg getOutput() {
    return this.output;
  }

  /**
   * {@inheritDoc} 
   */
  @Override
  public String getDisplayableMessage() {
    return this.output.getDisplayableMessage();
  }
  
  
   

}
