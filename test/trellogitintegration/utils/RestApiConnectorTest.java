package trellogitintegration.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import org.junit.Test;

import trellogitintegration.json.SampleJsonData;
import trellogitintegration.rest.JsonStringConverter;
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

    String reply = RestApiConnector.post(root + "/posts", jsonObject);
    jsonObject.setId(101);  // fixed pseudo id 
    assertEquals(jsonObject, JsonStringConverter.toObject(reply, SampleJsonData.class));
  }

  @Test
  public void putTest() throws IOException {
    SampleJsonData jsonObject = new SampleJsonData();
    jsonObject.setUserId(1);
    jsonObject.setBody("body");
    jsonObject.setTitle("title");

    String reply = RestApiConnector.put(root + "/posts/1", jsonObject);
    jsonObject.setId(1);     // fixed pseudo id 
    assertEquals(jsonObject, JsonStringConverter.toObject(reply, SampleJsonData.class));
  }

  @Test
  public void deleteTest() throws IOException {
    int statusCode = RestApiConnector.delete(root + "/posts/1");
    assertEquals(HttpURLConnection.HTTP_OK, statusCode);
  }

}
