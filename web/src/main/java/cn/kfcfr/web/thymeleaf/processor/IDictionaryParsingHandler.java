package cn.kfcfr.web.thymeleaf.processor;

import java.util.Map;

/**
 * Created by zhangqf on 2017/6/8.
 */
public interface IDictionaryParsingHandler {
    public String getTextByKey(Object key, Map<String, Object> map);
}
