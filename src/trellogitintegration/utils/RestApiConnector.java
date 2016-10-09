package trellogitintegration.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * REST API Connector that provides access to an URL using GET, 
 * PUT, POST, and DELETE request
 * 
 * Created: Oct 8, 2016
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
	 *            the target URL
	 * @param pojoClass
	 *            Class that the JSON String should be deserialized to. Must
	 *            contain a default constructor
	 * @return the object deserialized from the JSON string from the GET request
	 * @throws IOException
	 *             when connection goes wrong
	 */
	public static <T> T get(String url, Class<T> pojoClass) throws IOException {
		HttpURLConnection connection = configureConnection(url);
		connection.setRequestMethod(GET);
		connection.connect();
		StringBuilder result = new StringBuilder();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		reader.close();
		connection.disconnect();
		T jsonObject = MAPPER.readValue(result.toString(), pojoClass);
		return jsonObject;

	}

	/**
	 * Sends a POST request to the url with the provided pojoObject
	 * 
	 * @param url
	 *            the target URL
	 * @param pojoObject
	 *            Object to send to the URL
	 * @return status code
	 * @throws IOException
	 *             when connection goes wrong
	 */
	public static <T> int post(String url, T pojoObject) throws IOException {
		return writeToUrl(url, POST, pojoObject);
	}

	/**
	 * Sends a PUT request to the url with the provided pojoObject
	 * 
	 * @param url
	 *            the target URL
	 * @param pojoObject
	 *            Object to send to the URL
	 * @return status code
	 * @throws IOException
	 *             when connection goes wrong
	 */
	public static <T> int put(String url, T pojoObject) throws IOException {
		return writeToUrl(url, PUT, pojoObject);
	}

	/**
	 * Sends a DELETE request to the url
	 * 
	 * @param url
	 *            the target URL
	 * @return
	 * @throws IOException
	 */
	public static int delete(String url) throws IOException {
		HttpURLConnection connection = configureConnection(url);
		connection.setRequestMethod(DELETE);
		connection.connect();
		return connection.getResponseCode();
	}

	/**
	 * Convert pojoObject to JSON String and write it to the url using the
	 * specified method
	 * 
	 * @param url
	 *            the target URL
	 * @param method
	 *            Method to write to the url (PUT, POST)
	 * @param pojoObject
	 *            Object to be sent
	 * @return status code
	 * @throws IOException
	 */
	private static <T> int writeToUrl(String url, String method, T pojoObject) throws IOException {
		HttpURLConnection connection = configureConnection(url);
		connection.setRequestMethod(method);
		connection.connect();

		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(connection.getOutputStream(), Charset.forName("UTF-8")));
		String jsonString = MAPPER.writeValueAsString(pojoObject);
		writer.write(jsonString);
		writer.flush();
		writer.close();
		connection.disconnect();
		return connection.getResponseCode();
	}

	/**
	 * Configure Http Connection to default options.
	 * <p>
	 * <ul>
	 * <li>Content-Type: application/json
	 * <li>User-Agent: Browers
	 * <li>Connection Timeout: 5 seconds
	 * <li>Reader Timeout: 5 seconds
	 * <li>Readable
	 * <li>Writable
	 * </ul>
	 * 
	 * @param urlString
	 *            the target url
	 * @retur a configured HttpURLConnection object
	 * @throws IOException
	 */
	private static HttpURLConnection configureConnection(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		connection.setConnectTimeout(5000);// 5 secs
		connection.setReadTimeout(5000);// 5 secs
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;

	}

}
