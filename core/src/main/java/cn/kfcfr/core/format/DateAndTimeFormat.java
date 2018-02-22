package cn.kfcfr.core.format;

import cn.kfcfr.core.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class DateAndTimeFormat {
    private static SimpleDateFormat sdfTimeStamp = new SimpleDateFormat(CommonConstant.TIMESTAMP_FORMAT);

    /***
     * 日期时间格式化成yyyy-MM-dd hh24:mm:ss.SSS
     * @param date 要格式化的日期时间
     * @return 字符串结果
     */
    public static String format4TimeStamp(Date date) {
        if (date == null) return "";
        return sdfTimeStamp.format(date);
    }

    /***
     * 按指定格式格式化日期时间
     * @param date 要格式化的日期时间
     * @param format 指定格式
     * @return 字符串结果
     */
    public static String format(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /***
     * 日期字符串格式转换
     * @param value 日期字符串
     * @param fromFormat 转换前格式
     * @param toFormat 转换后格式
     * @return 转换后日期字符串
     * @throws ParseException 转换抛出的异常
     */
    public static String convert(String value, String fromFormat, String toFormat) throws ParseException {
        if (StringUtils.isBlank(value)) return value;
        SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat);
        SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat);
        Date dtValue = sdfFrom.parse(value);
        return sdfTo.format(dtValue);
    }

//    /***
//     * 日期字符串转成日期
//     * @param value 日期字符串
//     * @param format 日期字符串的格式
//     * @return 转换后的日期
//     * @throws ParseException 转换抛出的异常
//     */
//    public static Date parse(String value, String format) throws ParseException {
//        if (StringUtils.isBlank(value)) return null;
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        return sdf.parse(value);
//    }
}
