package cn.kfcfr.core.math;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class LongCalcTest {
    @Test
    public void powDecimal() throws Exception {
        int base = 10;
        int exponent = 16;
        long rst = LongCalc.powDecimal(base, exponent);
        assertThat("Long幂数16计算错误", rst, equalTo(10000000000000000L));
    }

    @Test
    public void powDecimal_e0() throws Exception {
        int base = 10;
        int exponent = 0;
        long rst = LongCalc.powDecimal(base, exponent);
        assertThat("Long幂数0计算错误", rst, equalTo(1L));
    }

}