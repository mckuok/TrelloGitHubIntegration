package trellogitintegration.exec.git.github;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import trellogitintegration.exec.git.github.pullrequest.PullRequest;
import trellogitintegration.exec.git.github.pullrequest.PullRequestResult;
import trellogitintegration.rest.RestApiConnector;

public class GitHubApiCaller {

  
  public static PullRequestResult createPullRequest(PullRequest pullRequest, GitHubInfo gitHubInfo) throws IOException {
    String reply = RestApiConnector.post(GitHubURLs.PULL_REQUST.getUrl(gitHubInfo.getUsername(), gitHubInfo.getRepo()), constructHeaderProperties(gitHubInfo), pullRequest);
    return new PullRequestResult(reply);
  }
  
  
  private static Map<String, String> constructHeaderProperties(GitHubInfo gitHubInfo) {
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", String.format("token %s", gitHubInfo.getToken()));
    
    return header;
  }
}
