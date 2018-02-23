package cn.kfcfr.core.math;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class DateCalc {

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
     * 计算指定月份的上月
     */
    public static String calcPreviousMonth(String month, String format) throws ParseException {
        return calcMonth(month, -1, format);
    }

    /***
     * 得到指定月份的最后一天
     */
    public static Date getLastDayOfMonth(String month, String format) throws ParseException {
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
     * 指定年/月/日/时/分/秒的加减
     */
    protected static Date add(Date value, int distance, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.add(field, distance);
        return calendar.getTime();
    }

    /***
     * 年加减
     */
    public static Date addYear(Date value, int distance) {
        return add(value, distance, Calendar.YEAR);
    }

    /***
     * 月份加减
     */
    public static Date addMonth(Date value, int distance) {
        return add(value, distance, Calendar.MONTH);
    }

    /***
     * 日加减
     */
    public static Date addDay(Date value, int distance) {
        return add(value, distance, Calendar.DATE);
    }

    /***
     * 时加减
     */
    public static Date addHour(Date value, int distance) {
        return add(value, distance, Calendar.HOUR_OF_DAY);
    }

    /***
     * 分加减
     */
    public static Date addMinute(Date value, int distance) {
        return add(value, distance, Calendar.MINUTE);
    }

    /***
     * 秒加减
     */
    public static Date addSecond(Date value, int distance) {
        return add(value, distance, Calendar.SECOND);
    }

    /***
     * 计算2个日期间的天数差值
     */
    public static Integer diffDay(Date begin, Date end) {
        if (begin == null || end == null) return null;
        Long day = (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
        return day.intValue();
    }

//    /***
//     * 计算指定月份的月份加减结果
//     */
//    public static String dateToString(Date date, String format) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        return sdf.format(date.getTime());
//    }

//    /***
//     * 时间格式化
//     */
//    public static String calcMonth(String month, int monthDiff, String fromformat,String toformat) throws ParseException {
//        if (StringUtils.isBlank(month)) return "";
//        SimpleDateFormat sdf = new SimpleDateFormat(fromformat);
//        Date date = sdf.parse(month);
//        SimpleDateFormat sdf1 = new SimpleDateFormat(toformat);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.MONTH, monthDiff);
//        return sdf1.format(calendar.getTime());
//    }

}
