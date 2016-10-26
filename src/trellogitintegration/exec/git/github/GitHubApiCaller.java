package trellogitintegration.exec.git.github;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import trellogitintegration.exec.git.github.pullrequest.PullRequest;
import trellogitintegration.exec.git.github.pullrequest.PullRequestResult;
import trellogitintegration.rest.RestApiConnector;

public class GitHubApiCaller {

  private final GitHubInfo gitHubInfo;
  private final Map<String, String> headerProperties;  
  
  public GitHubApiCaller(GitHubInfo gitHubInfo) {
    this.gitHubInfo = gitHubInfo;
    this.headerProperties = constructHeaderProperties();
  }
  
  public PullRequestResult createPullRequest(PullRequest pullRequest) throws IOException {
    String reply = RestApiConnector.post(GitHubURLs.PULL_REQUST.getUrl(gitHubInfo.getUsername(), gitHubInfo.getRepo()), headerProperties, pullRequest);
    return new PullRequestResult(reply);
  }
  
  
  private Map<String, String> constructHeaderProperties() {
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", String.format("token %s", this.gitHubInfo.getToken()));
    
    return header;
  }
}
