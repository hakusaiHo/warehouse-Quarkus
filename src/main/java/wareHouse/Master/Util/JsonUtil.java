package wareHouse.Master.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.logging.Log;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonUtil() {
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Log.error("Occur error during parsing data to json: {}", obj, e);
            return "";
        }
    }

    public static <T> T toObject(String json, Class<T> objectClass) {
        try {
            return objectMapper.readValue(json, objectClass);
        } catch (IOException e) {
            Log.error("Occur error during mapping json to an object: {}", json, e);
            return null;
        }
    }

    public static <T> List<T> toListObject(String jsonArray, Class<T> objectClass) {
        try {
            JavaType getCollectionType = objectMapper.getTypeFactory().constructParametricType(List.class, objectClass);
            return objectMapper.readValue(jsonArray, getCollectionType);
        } catch (IOException e) {
            Log.error("Occur error during mapping json to an array: []", jsonArray, e);
            return new ArrayList<>();
        }
    }
}