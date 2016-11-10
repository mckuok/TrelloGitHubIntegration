package trellogitintegration.exec.git.github;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import trellogitintegration.exec.git.github.pullrequest.PullRequest;
import trellogitintegration.exec.git.github.pullrequest.PullRequestResult;
import trellogitintegration.persist.config.ProjectConfig.GitConfig;
import trellogitintegration.rest.RestApiConnector;
import trellogitintegration.utils.ValidationUtils;

/**
 * This class contains methods to call the GitHub Api
 * Created: Nov 3, 2016
 * @author Man Chon Kuok
 */
public class GitHubApiCaller {

  /**
   * Submits a pull request using the given pull request and github account info
   * @param pullRequest pull request object that contains info about the pull request
   * @param gitHubInfo github account info
   * @return the result of the pull rquest
   * @throws IOException
   */
  public static PullRequestResult createPullRequest(PullRequest pullRequest, GitConfig gitHubInfo) throws IOException {
    ValidationUtils.checkNull(pullRequest, gitHubInfo);
    
    String reply = RestApiConnector.post(GitHubURLs.PULL_REQUST.getUrl(gitHubInfo.getUsername(), gitHubInfo.getRepo()), constructHeaderProperties(gitHubInfo), pullRequest);
    return new PullRequestResult(reply);
  }
  
  /**
   * Construct a header for the HTTPS request
   * @param gitHubInfo github account info
   * @return the map of header properties
   */
  private static Map<String, String> constructHeaderProperties(GitConfig gitHubInfo) {
    ValidationUtils.checkNull(gitHubInfo);
    
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", String.format("token %s", gitHubInfo.getToken()));
    
    return header;
  }
}
