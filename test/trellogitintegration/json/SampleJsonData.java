package trellogitintegration.json;

public class SampleJsonData {

  private int userId;
  private int id;
  private String title;
  private String body;

  public SampleJsonData() {
    userId = 0;
    id = 0;
    setTitle("");
    setBody("");
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "JsonData [userId=" + userId + ", id=" + id + ", title=" + title
        + ", body=" + body + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((body == null) ? 0 : body.hashCode());
    result = prime * result + id;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + userId;
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
    SampleJsonData other = (SampleJsonData) obj;
    if (body == null) {
      if (other.body != null)
        return false;
    } else if (!body.equals(other.body))
      return false;
    if (id != other.id)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (userId != other.userId)
      return false;
    return true;
  }

}
