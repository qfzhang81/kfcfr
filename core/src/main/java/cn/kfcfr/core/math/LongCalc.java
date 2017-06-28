package cn.kfcfr.core.math;

/**
 * Created by zhangqf on 2017/6/8.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class LongCalc {
    /***
     * 10进制的次方计算
     * @param base  根数
     * @param exponent  指数
     * @return base的exponent次方
     */
    public static long powDecimal(long base, long exponent) {
        //采用递归的方法，假设已经知道x的m次方，则若m为偶数，则x的n次方等译(x^m)^2,否则等于x(x^m)^2
        long y;
        if (exponent == 0) {
            y = 1;
        }
        else {
            y = powDecimal(base, exponent / 2);
            y = y * y;
            if (exponent % 2 != 0) {
                y = base * y;
            }
        }
        return y;
    }
}
