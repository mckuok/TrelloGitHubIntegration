package trellogitintegration.exec.git.github;

public enum GitHubURLs {

  PULL_REQUST("https://api.github.com/repos/%s/%s/pulls");
  
  private String url;
  
  GitHubURLs(String url) {
    this.url = url;
  }
  
  public String getUrl(String username, String repo) {
    return String.format(this.url, username, repo);
  }
}
