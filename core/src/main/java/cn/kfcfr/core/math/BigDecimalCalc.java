package cn.kfcfr.core.math;

import java.math.BigDecimal;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class BigDecimalCalc {
    /***
     * 指定小数位四舍五入
     */
    public static BigDecimal round(double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /***
     * 指定小数位进位
     */
    public static BigDecimal ceiling(double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_CEILING);
    }

    /***
     * 指定小数位舍去尾数
     */
    public static BigDecimal floor(double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_FLOOR);
    }

    /***
     * 加法
     */
    public static BigDecimal add(BigDecimal... values) {
        BigDecimal rst = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value == null) continue;
            rst = rst.add(value);
        }
        return rst;
    }

    /***
     * 减法
     */
    public static BigDecimal subtract(BigDecimal minuend, BigDecimal... subtrahends) {
        BigDecimal rst = minuend;
        for (BigDecimal value : subtrahends) {
            if (value == null) continue;
            rst = rst.subtract(value);
        }
        return rst;
    }

    /***
     * 乘法
     */
    public static BigDecimal multiply(BigDecimal... values) {
        BigDecimal rst = BigDecimal.ONE;
        for (BigDecimal value : values) {
            if (value == null) continue;
            rst = rst.multiply(value);
        }
        return rst;
    }

    /***
     * 除法
     */
    public static BigDecimal divide(int scale, BigDecimal dividend, BigDecimal... divisors) {
        BigDecimal rst = dividend;
        for (BigDecimal value : divisors) {
            if (value == null) continue;
            rst = rst.divide(value, scale, BigDecimal.ROUND_HALF_UP);
        }
        return rst;
    }
}
