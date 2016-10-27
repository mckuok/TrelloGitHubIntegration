package trellogitintegration.exec.git.github.pullrequest;

import java.util.Date;
import trellogitintegration.exec.git.github.user.User;

public class PullRequestSuccessMsg extends PullRequestResultMsg {

  private String html_url;
  private String title;
  private String body;
  private String state;
  private Date created_at;
  private Date updated_at;
  private Date closed_at;
  private Date merged_at;
  private User user;
  /**
   * {@inheritDoc} 
   */
  @Override
  public String getDisplayableMessage() {
    return String.format("Pull Request submitted\n Title: \t%s\nBody: \t%s\nLink:%s", this.title, this.body, this.html_url);
  }
  
    
}
