package cn.kfcfr.core.generator;

import java.util.Calendar;
import java.util.Date;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class GenerateDateUtils {
    /***
     * 根据年月日生成日期类型
     * @param year 年份
     * @param month 月份，取值0-11
     * @param day 日
     * @return 日期类型
     */
    public static Date generateOne(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
