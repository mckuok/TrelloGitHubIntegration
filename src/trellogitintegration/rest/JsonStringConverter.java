package trellogitintegration.rest;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to handle JSON formatted string to Object and Object to JSON
 * formatted string Created: Oct 22, 2016
 * 
 * @author Man Chon Kuok
 */
public class JsonStringConverter {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  static {
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
  /**
   * Convert a pojo to String
   * 
   * @param pojo
   *          pojo that contains primitive types + List
   * @return a JSON formatted string
   * @throws JsonProcessingException
   */
  public static <T> String toString(T pojo) throws JsonProcessingException {
    return MAPPER.writeValueAsString(pojo);
  }

  /**
   * Convert a JSON format String to an Object
   * 
   * @param json
   *          the JSON formatted string
   * @param pojoClass
   *          class for the data structure to convert the string to
   * @return the pojo
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  public static <T> T toObject(String json, Class<T> pojoClass)
      throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(json, pojoClass);
  }

}
