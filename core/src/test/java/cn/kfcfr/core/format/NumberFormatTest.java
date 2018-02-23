package cn.kfcfr.core.format;

import cn.kfcfr.core.convert.LongConvert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class NumberFormatTest {
    @Test
    public void decimalPadLeft() throws Exception {
        long value = 1234567;
        int length = 10;
        String rst = LongConvert.padLeft(value, length);
        assertThat("7位数字左补3个0错误", rst, equalTo("0001234567"));
    }

}