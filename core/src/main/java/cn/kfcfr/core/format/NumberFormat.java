package cn.kfcfr.core.format;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class NumberFormat {

    /***
     * 10进制左补齐0
     * @param value 数字
     * @param length 补齐后长度
     * @return 补齐后的数字字符串
     */
    public static String decimalPadLeft(long value, int length) {
        return String.format("%0" + length + "d", value);
    }

    public static String format4Original(Integer value) {
        if (value == null) return "";
        return value.toString();
    }

    public static String format4Original(Long value) {
        if (value == null) return "";
        return value.toString();
    }
}
