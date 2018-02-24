//package cn.kfcfr.ztestcommon.utils;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
//@Component
//public class JsonUtils {
//
//    public static ObjectMapper createJsonMapper() {
//        return new ObjectMapper();
//    }
//
//    public static String serialize(Object value) throws JsonProcessingException {
//        return createJsonMapper().writeValueAsString(value);
//    }
//
//    public static <T> T deserialize(String value, Class<T> clazz) throws IOException {
//        return createJsonMapper().readValue(value, clazz);
//    }
//    public static String toJsonString(Object value) {
//
//        String str = "{}";
//        try {
//            str = createJsonMapper().writeValueAsString(value);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
//}
