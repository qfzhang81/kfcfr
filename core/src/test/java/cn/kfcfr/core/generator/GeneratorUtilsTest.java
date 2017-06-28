package cn.kfcfr.core.generator;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class GeneratorUtilsTest {
    @Test
    public void generateLong() throws Exception {
        long aaa = GeneratorUtils.generateLong();
        System.out.println("generateLong:"+aaa);
        assertThat("生成失败", aaa, greaterThan(0L));
    }

    @Test
    public void generateLongList() throws Exception {
        int length = 3;
        long aaa[] = GeneratorUtils.generateLongList(length);
        System.out.println("generateLongList.first:"+aaa[0]);
        System.out.println("generateLongList.last:"+aaa[length-1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
    }

    @Test
    public void generateLongList_overflow() throws Exception {
        int length = 150000;
//        try {
//            long aaa[] = GeneratorUtils.generateLongList(length);
//            fail("Expected an IllegalArgumentException to be thrown");
//        }
//        catch (IllegalArgumentException ex) {
//            ex.printStackTrace();
//            assertThat(ex.getMessage(), containsString("length cannot greater than"));
//        }
        long aaa[] = GeneratorUtils.generateLongList(length);
        System.out.println("generateLongList_overflow.first:"+aaa[0]);
        System.out.println("generateLongList_overflow.last:"+aaa[length-1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
    }

    @Test
    public void generateLongList_2nd() throws Exception {
        int length = 80000;
        System.out.println("generateLongList_2nd:1st");
        long aaa[] = GeneratorUtils.generateLongList(length);
        System.out.println("generateLongList_2nd.first:"+aaa[0]);
        System.out.println("generateLongList_2nd.last:"+aaa[length-1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList_2nd:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
        //2nd
        length = 60000;
        System.out.println("generateLongList_2nd:2nd");
        aaa = GeneratorUtils.generateLongList(length);
        System.out.println("generateLongList_2nd.first:"+aaa[0]);
        System.out.println("generateLongList_2nd.last:"+aaa[length-1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList_2nd:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
    }

}