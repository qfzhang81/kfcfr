package cn.kfcfr.core.convert;

import cn.kfcfr.core.exception.WrappedException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

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
            throw new WrappedException(MessageFormat.format("Serialize {0} to json failed.", value), ex);
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
            throw new WrappedException(MessageFormat.format("Deserialize {0} to {1} from json failed.", value, clazz), ex);
        }
    }

    /***
     * 反序列化成列表
     * @param value 需要反序列化的字符串
     * @param listClazz 列表类型
     * @param elementClazz 对象类型
     * @param <T> 对象类型
     * @return 对象列表
     */
    public static <T> List<T> deserializeList(String value, Class<? extends List> listClazz, Class<T> elementClazz) {
        ObjectMapper mapper = createJsonMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(listClazz, elementClazz);
        try {
            return mapper.readValue(value, javaType);
        }
        catch (IOException ex) {
            throw new WrappedException(MessageFormat.format("Deserialize {0} to a {1} of {2} from json failed.", value, listClazz, elementClazz), ex);
        }
    }

    /***
     * 反序列化成Map
     * @param value 需要反序列化的字符串
     * @param mapClazz Map类型
     * @param keyClazz Map中键类型
     * @param valueClazz Map中值类型
     * @param <K> Map中键类型
     * @param <V> Map中值类型
     * @return Map
     */
    public static <K, V> Map<K, V> deserializeMap(String value, Class<? extends Map> mapClazz, Class<K> keyClazz, Class<V> valueClazz) {
        ObjectMapper mapper = createJsonMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(mapClazz, keyClazz, valueClazz);
        try {
            return mapper.readValue(value, javaType);
        }
        catch (IOException ex) {
            throw new WrappedException(MessageFormat.format("Deserialize {0} to a {1} of <{2},{3}> from json failed.", value, mapClazz, keyClazz, valueClazz), ex);
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
