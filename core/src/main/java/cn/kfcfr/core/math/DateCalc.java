package cn.kfcfr.core.math;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class DateCalc {

    /***
     * 计算指定月份的上月
     */
    public static String calcLastMonth(String month, String format) throws ParseException {
        return calcMonth(month, -1, format);
    }

    /***
     * 计算指定月份的月份加减结果
     */
    public static String calcMonth(String month, int monthDiff, String format) throws ParseException {
        if (StringUtils.isBlank(month)) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthDiff);
        return sdf.format(calendar.getTime());
    }

    /***
     * 月份加减
     */
    public static Date addMonth(Date value, int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.add(Calendar.MONTH, diff);
        return calendar.getTime();
    }

    /***
     * 得到指定月份的最后一天
     */
    public static Date calcLastDayOfMonth(String month, String format) throws ParseException {
        if (StringUtils.isBlank(month)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /***
     * 计算2个日期间的差值
     */
    public static Integer diffDay(Date begin, Date end) {
        if (begin == null || end == null) return null;
        Long day = (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
        return day.intValue();
    }

    /***
     * 得到指定月份的天数
     */
    public static Integer getDayOfMonth(String month, String format) throws ParseException {
        if (StringUtils.isBlank(month)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /***
     * 计算指定月份的月份加减结果
     */
    public static String dateToString(Date date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date.getTime());
    }

    /***
     * 时间格式化
     */
    public static String calcMonth(String month, int monthDiff, String fromformat,String toformat) throws ParseException {
        if (StringUtils.isBlank(month)) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(fromformat);
        Date date = sdf.parse(month);
        SimpleDateFormat sdf1 = new SimpleDateFormat(toformat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthDiff);
        return sdf1.format(calendar.getTime());
    }

}
