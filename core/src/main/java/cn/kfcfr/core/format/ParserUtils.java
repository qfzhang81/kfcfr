package cn.kfcfr.core.format;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class ParserUtils {

    /***
     * 字符串转数字int型
     * @param value 字符串
     * @return int型结果，如是空字符串，返回null
     */
    public static Integer string2Int(String value) {
        if (StringUtils.isBlank(value)) {
            return  null;
        }
        return Integer.parseInt(value);
    }

    /***
     * 日期字符串转成日期
     * @param value 日期字符串
     * @param format 日期字符串的格式
     * @return 转换后的日期
     * @throws ParseException 转换抛出的异常
     */
    public static Date parse(String value, String format) throws ParseException {
        if (StringUtils.isBlank(value)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(value);
    }
}
