package cn.kfcfr.core.convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by zhangqf77 on 2018/2/28.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class JsonConvert {
    public static ObjectMapper createJsonMapper() {
        return new ObjectMapper();
    }

    /***
     * 序列化成字符串
     * @param value 需要序列化的对象
     * @return 字符串
     */
    public static String serialize(Object value) {
        try {
            return createJsonMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(value);
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /***
     * 反序列化成对象
     * @param value 需要反序列化的字符串
     * @param clazz 对象类型
     * @param <T> 对象类型
     * @return 对象
     */
    public static <T> T deserialize(String value, Class<T> clazz) {
        try {
            return createJsonMapper().readValue(value, clazz);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /***
     * 对象转成JSON字符串。如有异常，返回异常信息字符串
     * @param value 对象
     */
    public static String toJsonString(Object value) {
        String str = "{}";
        try {
            if (value != null) {
                str = createJsonMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(value);
            }
        }
        catch (JsonProcessingException ex) {
            str = ex.getMessage();
        }
        return str;
    }
}
