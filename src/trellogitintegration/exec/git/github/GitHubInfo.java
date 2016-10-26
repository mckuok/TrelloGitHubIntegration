package trellogitintegration.exec.git.github;

public class GitHubInfo {

  private final String username;
  private final String repo;
  private final String token;
  
  public GitHubInfo(String username, String repo, String token) {
    this.username = username;
    this.repo = repo;
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public String getRepo() {
    return repo;
  }

  public String getToken() {
    return token;
  }
  
}
