package cn.kfcfr.core.math;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class IntCalcTest {
    @Test
    public void powDecimal() throws Exception {
        int base = 10;
        int exponent = 5;
        int rst = IntCalc.powDecimal(base, exponent);
        assertThat("Int幂数5计算错误", rst, equalTo(100000));
    }

    @Test
    public void powDecimal_e0() throws Exception {
        int base = 10;
        int exponent = 0;
        int rst = IntCalc.powDecimal(base, exponent);
        assertThat("Int幂数0计算错误", rst, equalTo(1));
    }

    @Test
    public void powDecimal_overflow() throws Exception {
        int base = 10;
        int exponent = 16;
        try {
            int rst = IntCalc.powDecimal(base, exponent);
            fail("Expected an ClassCastException to be thrown");
        }
        catch (ClassCastException ex) {
            System.out.println(ex);
            assertThat(ex.getMessage(), containsString("exceeds the maximum of integer"));
        }
    }

}