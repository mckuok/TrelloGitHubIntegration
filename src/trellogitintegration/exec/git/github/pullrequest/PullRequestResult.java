package trellogitintegration.exec.git.github.pullrequest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import trellogitintegration.exec.OperationResult;
import trellogitintegration.rest.JsonStringConverter;

public class PullRequestResult extends OperationResult<PullRequestResultMsg> {

  private static final String FAILURE = "Failed";
  private final PullRequestResultMsg output;
  
  public PullRequestResult(String pullRequestReply) throws JsonParseException, JsonMappingException, IOException {
    super(!pullRequestReply.contains(FAILURE));
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
  
   

}
