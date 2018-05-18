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
     * @throws JsonProcessingException 转换失败抛出的异常
     */
    public static String serialize(Object value) throws JsonProcessingException {
        return createJsonMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(value);
    }

    /***
     * 反序列化成对象
     * @param value 需要反序列化的字符串
     * @param clazz 对象类型
     * @param <T> 对象类型
     * @return 对象
     * @throws IOException 转换失败抛出的异常
     */
    public static <T> T deserialize(String value, Class<T> clazz) throws IOException {
        return createJsonMapper().readValue(value, clazz);
    }

    /***
     * 对象转成JSON字符串
     * @param value 对象
     * @return JSON字符串。有异常不抛出
     */
    public static String toJsonString(Object value) {
        return toJsonString(value, false);
    }

    /***
     * 对象转成JSON字符串
     * @param value 对象
     * @param throwException 如有异常是否抛出
     * @return JSON字符串
     */
    public static String toJsonString(Object value, boolean throwException) {
        String str = "{}";
        try {
            str = createJsonMapper().writeValueAsString(value);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            if (throwException) {
                throw new RuntimeException(e);
            }
        }
        return str;
    }
}
