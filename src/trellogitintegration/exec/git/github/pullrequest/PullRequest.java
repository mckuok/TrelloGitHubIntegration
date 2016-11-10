package trellogitintegration.exec.git.github.pullrequest;

public class PullRequest {

  private String title;
  private String head;
  private String base;
  private String body;
  
  public PullRequest(String title,
                     String head,
                     String base,
                     String body) {
    this.title = title;
    this.head = head;
    this.base = base;
    this.body = body;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHead() {
    return head;
  }

  public void setHead(String head) {
    this.head = head;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((base == null) ? 0 : base.hashCode());
    result = prime * result + ((body == null) ? 0 : body.hashCode());
    result = prime * result + ((head == null) ? 0 : head.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PullRequest other = (PullRequest) obj;
    if (base == null) {
      if (other.base != null)
        return false;
    } else if (!base.equals(other.base))
      return false;
    if (body == null) {
      if (other.body != null)
        return false;
    } else if (!body.equals(other.body))
      return false;
    if (head == null) {
      if (other.head != null)
        return false;
    } else if (!head.equals(other.head))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }
  
  
  
  
}
