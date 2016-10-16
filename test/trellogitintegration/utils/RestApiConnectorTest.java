package trellogitintegration.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Test;

import trellogitintegration.json.SampleJsonData;
import trellogitintegration.rest.RestApiConnector;

public class RestApiConnectorTest {

  private static final String root = "https://jsonplaceholder.typicode.com";

  @Test
  public void getTest() throws IOException {
    SampleJsonData expectedObject = new SampleJsonData();
    expectedObject.setId(1);
    expectedObject.setUserId(1);
    expectedObject.setBody(
        "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
    expectedObject.setTitle(
        "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");

    SampleJsonData returnedObject = RestApiConnector.get(root + "/posts/1",
        SampleJsonData.class);
    assertEquals(expectedObject, returnedObject);
  }

  @Test
  public void postTest() throws IOException {
    SampleJsonData jsonObject = new SampleJsonData();
    jsonObject.setUserId(1);
    jsonObject.setBody("body");
    jsonObject.setTitle("title");

    int statusCode = RestApiConnector.post(root + "/posts", jsonObject);
    assertEquals(HttpURLConnection.HTTP_CREATED, statusCode);
  }

  @Test
  public void putTest() throws IOException {
    SampleJsonData jsonObject = new SampleJsonData();
    jsonObject.setUserId(1);
    jsonObject.setBody("body");
    jsonObject.setTitle("title");

    int statusCode = RestApiConnector.put(root + "/posts/1", jsonObject);
    assertEquals(HttpURLConnection.HTTP_OK, statusCode);
  }

  @Test
  public void deleteTest() throws IOException {
    int statusCode = RestApiConnector.delete(root + "/posts/1");
    assertEquals(HttpURLConnection.HTTP_OK, statusCode);
  }

}
