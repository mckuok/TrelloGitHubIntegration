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
  
    
}
