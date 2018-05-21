package cn.kfcfr.core.convert;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangqf77 on 2018/2/22.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class StringConvert {
    /***
     * 去掉字符串末尾的指定字符
     * @param value 字符串
     * @param trimAscii 要去掉的指定字符ASCII
     * @return 去除后的字符串结果
     */
    public static String trimLast(String value, int[] trimAscii) {
        if (StringUtils.isEmpty(value) || trimAscii == null || trimAscii.length == 0) return value;
        String rst = value;
        char[] chars = rst.toCharArray();
        int index = chars.length;
        boolean checkLast = true;
        while (index > 0 && checkLast) {
            checkLast = false;
            byte a = (byte) chars[index - 1];
            for (int b : trimAscii) {
                if (a == b) {
                    //要去掉
                    checkLast = true;
                    index--;
                    break;
                }
            }
//            for (int i = 0; i < trimAscii.length; i++) {
//                if (a == trimAscii[i]) {
//                    //要去掉
//                    checkLast = true;
//                    index--;
//                    break;
//                }
//            }
        }
        if (index > 0) {
            rst = rst.substring(0, index);
        }
        else {
            rst = "";
        }
        return rst;
    }
}
