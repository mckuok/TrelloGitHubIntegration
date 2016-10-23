package trellogitintegration.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import trellogitintegration.persist.IOUtils;

/**
 * REST API Connector that provides access to an URL using GET, PUT, POST, and
 * DELETE request
 * 
 * Created: Oct 8, 2016
 * 
 * @author Man Chon Kuok
 */
public class RestApiConnector {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String GET = "GET";
  private static final String PUT = "PUT";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";

  /**
   * Sends a GET request to the url, and get back an object deserialized from
   * the JSON string
   * 
   * @param url
   *          the target URL
   * @param headerProperties
   *          Extra properties to insert to the HTTP / HTTPS header
   * @param returnClass
   *          Class that the JSON String should be deserialized to. Must contain
   *          a default constructor
   * @return the object deserialized from the JSON string from the GET request
   * @throws IOException
   *           when connection goes wrong
   */
  public static <T> T get(String url, Map<String, String> headerProperties,
      Class<T> returnClass) throws IOException {
    HttpURLConnection connection = configureConnection(url, headerProperties);
    connection.setRequestMethod(GET);
    connection.connect();

    String content = IOUtils.readFromStream(connection.getInputStream());
    connection.disconnect();
    T jsonObject = JsonStringConverter.toObject(content, returnClass);
    return jsonObject;

  }

  /**
   * Sends a GET request to the url, and get back an object deserialized from
   * the JSON string
   * 
   * @param url
   *          the target URL
   * @param returnClass
   *          Class that the JSON String should be deserialized to. Must contain
   *          a default constructor
   * @return the object deserialized from the JSON string from the GET request
   * @throws IOException
   *           when connection goes wrong
   */
  public static <T> T get(String url, Class<T> returnClass) throws IOException {
    return get(url, new HashMap<String, String>(), returnClass);
  }

  /**
   * Sends a POST request to the url with the provided pojoObject and return the
   * reply as a String
   * 
   * @param <S>
   * 
   * @param url
   *          the target URL
   * @param headerProperties
   *          Extra properties to insert to the HTTP / HTTPS header
   * @param pojoObject
   *          Object to send to the URL
   * @return the reply as a String
   * @throws IOException
   *           when connection goes wrong
   */
  public static <T> String post(String url,
      Map<String, String> headerProperties, T pojoObject) throws IOException {
    return writeToUrl(url, headerProperties, POST, pojoObject);
  }

  /**
   * Sends a POST request to the url with the provided pojoObject and return the
   * reply as a String
   * 
   * @param <S>
   * 
   * @param url
   *          the target URL
   * @param pojoObject
   *          Object to send to the URL
   * @return the reply as a String
   * @throws IOException
   *           when connection goes wrong
   */
  public static <T> String post(String url, T pojoObject) throws IOException {
    return post(url, new HashMap<String, String>(), pojoObject);
  }

  /**
   * Sends a PUT request to the url with the provided pojoObject
   * 
   * @param url
   *          the target URL
   * @param headerProperties
   *          Extra properties to insert to the HTTP / HTTPS header
   * @param pojoObject
   *          Object to send to the URL
   * @return status code
   * @throws IOException
   *           when connection goes wrong
   */
  public static <T> String put(String url, Map<String, String> headerProperties,
      T pojoObject) throws IOException {
    return writeToUrl(url, headerProperties, PUT, pojoObject);
  }

  /**
   * Sends a PUT request to the url with the provided pojoObject
   * 
   * @param url
   *          the target URL
   * @param headerProperties
   *          Extra properties to insert to the HTTP / HTTPS header
   * @param pojoObject
   *          Object to send to the URL
   * @return status code
   * @throws IOException
   *           when connection goes wrong
   */
  public static <T> String put(String url, T pojoObject) throws IOException {
    return put(url, new HashMap<String, String>(), pojoObject);
  }

  /**
   * Sends a DELETE request to the url
   * 
   * @param url
   *          the target URL
   * @return
   * @throws IOException
   */
  public static int delete(String url, Map<String, String> headerProperties)
      throws IOException {
    HttpURLConnection connection = configureConnection(url, headerProperties);
    connection.setRequestMethod(DELETE);
    connection.connect();
    return connection.getResponseCode();
  }

  public static int delete(String url) throws IOException {
    return delete(url, new HashMap<String, String>());
  }

  /**
   * Write the pojoObject to the URL as a JSON string with the specified header
   * properties using the provided method.
   * 
   * @param url
   *          the target url
   * @param headerProperties
   *          header properties for the HTTP / HTTPS header
   * @param method
   *          method to write to the URL (GET, POST, PUT, DELETE, etc)
   * @param pojoObject
   *          object to be deserialized into JSON string
   * @return the reply from the request as a String
   * @throws IOException
   *           when connection goes wrong
   */
  private static <T> String writeToUrl(String url,
      Map<String, String> headerProperties, String method, T pojoObject)
      throws IOException {
    HttpURLConnection connection = configureConnection(url, headerProperties);
    connection.setRequestMethod(method);
    connection.connect();

    String jsonString = MAPPER.writeValueAsString(pojoObject);
    IOUtils.writeToStream(connection.getOutputStream(), jsonString);
    String reply = IOUtils.readFromStream(connection.getInputStream());

    connection.disconnect();
    return reply;
  }

  /**
   * Configure Http Connection to default options.
   * <p>
   * <ul>
   * <li>Content-Type: application/json
   * <li>User-Agent: Browsers
   * <li>Connection Timeout: 5 seconds
   * <li>Reader Timeout: 5 seconds
   * <li>Readable
   * <li>Writable
   * </ul>
   * 
   * @param urlString
   *          the target url
   * @retur a configured HttpURLConnection object
   * @throws IOException
   */
  private static HttpURLConnection configureConnection(String urlString,
      Map<String, String> headerProperties) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setRequestProperty("User-Agent",
        "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

    for (Entry<String, String> entry : headerProperties.entrySet()) {
      connection.setRequestProperty(entry.getKey(), entry.getValue());
    }

    connection.setConnectTimeout(5000);// 5 secs
    connection.setReadTimeout(5000);// 5 secs
    connection.setDoInput(true);
    connection.setDoOutput(true);
    return connection;
  }

}
