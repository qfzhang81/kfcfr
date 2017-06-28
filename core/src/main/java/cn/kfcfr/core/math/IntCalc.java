package cn.kfcfr.core.math;

import java.text.MessageFormat;

/**
 * Created by zhangqf on 2017/6/10.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class IntCalc {
    /***
     * 10进制的次方计算
     * @param base  根数
     * @param exponent  指数
     * @return base的exponent次方
     */
    public static int powDecimal(int base, int exponent) {
        long rst = LongCalc.powDecimal(base, exponent);
        if (rst > Integer.MAX_VALUE) {
            throw new ClassCastException(MessageFormat.format("The result {0} exceeds the maximum of integer.", rst));
        }
        return (int) rst;
    }
}
