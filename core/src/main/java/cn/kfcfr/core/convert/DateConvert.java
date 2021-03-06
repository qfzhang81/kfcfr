package cn.kfcfr.core.convert;

import cn.kfcfr.core.constant.DateTimeConstant;
import cn.kfcfr.core.exception.WrappedException;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf77 on 2018/2/22.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class DateConvert {
    /***
     * 日期字符串转成日期
     * @param value 日期字符串
     * @param format 日期字符串的格式
     * @return 转换后的日期
     */
    public static Date parseString(String value, String format) {
        if (StringUtils.isBlank(value)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return parseString(value, sdf);
    }

    /***
     * 日期字符串转成日期
     * @param value 日期字符串
     * @param sdf SimpleDateFormat对象
     * @return 转换后的日期
     */
    public static Date parseString(String value, SimpleDateFormat sdf) {
        try {
            return sdf.parse(value);
        }
        catch (ParseException ex) {
            throw new WrappedException(MessageFormat.format("{0} does not fit the format '{1}'.", value, sdf.toPattern()), ex);
        }
    }

    /***
     * 日期时间格式化成yyyy-MM-dd HH:mm:ss.SSS
     * @param date 要格式化的日期时间
     * @return 字符串结果
     */
    public static String formatTimeStamp(Date date) {
        return format(date, DateTimeConstant.TIMESTAMP_FORMAT);
    }

    /***
     * 日期时间格式化成yyyy-MM-dd
     * @param date 要格式化的日期时间
     * @return 字符串结果
     */
    public static String formatDate(Date date) {
        return format(date, DateTimeConstant.DATE_FORMAT);
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
     */
    public static String cast(String value, String fromFormat, String toFormat) {
        if (StringUtils.isBlank(value)) return value;
        SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat);
        SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat);
        Date dtValue = parseString(value, sdfFrom);
        return sdfTo.format(dtValue);
    }

}
