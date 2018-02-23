package cn.kfcfr.core.convert;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangqf77 on 2018/2/22.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class IntegerConvert {
    /***
     * 字符串转数字int型
     * @param value 字符串
     * @return int型结果，如是空字符串，返回null
     */
    public static Integer parseString(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    /***
     * 转成字符串
     * @param value 要转换的数值
     * @return 字符串，如果null，则为空字符串
     */
    public static String format(Integer value) {
        if (value == null) return "";
        return value.toString();
    }

    /***
     * 左补齐0
     * @param value 数字
     * @param length 补齐后长度
     * @return 补齐后的数字字符串
     */
    public static String padLeft(int value, int length) {
        return String.format("%0" + length + "d", value);
    }
}
