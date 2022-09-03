package pl.futurecollars.invoicing.db.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

@Component
class JsonService {

  private final ObjectMapper objectMapper;

  {
    objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  String convertToJson(Object object) {
    try {
      return new String(objectMapper.writeValueAsString(object).getBytes(), StandardCharsets.UTF_8);
    } catch (JsonProcessingException exception) {
      throw new RuntimeException(exception + "Failed to convert from object to JSON");
    }
  }

  <T> T toObject(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException exception) {
      throw new RuntimeException(exception + "Failed to convert from JSON to object");
    }
  }
}
