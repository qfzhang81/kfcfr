package cn.kfcfr.core.convert;

import cn.kfcfr.core.exception.WrappedException;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/2/22.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class StringConvert {
    /***
     * Byte数组转成字符串，指定编码
     * @param content Byte数组
     * @param encoding 编码
     * @return 字符串
     */
    public static String parseByte(byte[] content, String encoding) {
        try {
            return new String(content, encoding);
        }
        catch (UnsupportedEncodingException ex) {
            throw new WrappedException(MessageFormat.format("Convert bytes to string with encoding {0} failed.", encoding), ex);
        }
    }


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
