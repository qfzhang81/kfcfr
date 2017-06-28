package cn.kfcfr.core.format;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class NumberFormatTest {
    @Test
    public void decimalPadLeft() throws Exception {
        long value = 1234567;
        int lenth = 10;
        String rst = NumberFormat.decimalPadLeft(value, lenth);
        assertThat("7位数字左补3个0错误", rst, equalTo("0001234567"));
    }

}