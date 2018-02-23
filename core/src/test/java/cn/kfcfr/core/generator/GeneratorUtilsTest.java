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
        GenerateLongUtils generator = new GenerateLongUtilsImpl(2016, 1, 1, "8088");
        long aaa = generator.generateOne();
        System.out.println("generateLong:" + aaa);
        assertThat("生成失败", aaa, greaterThan(0L));
    }

    @Test
    public void generateLongList() throws Exception {
        GenerateLongUtils generator = new GenerateLongUtilsImpl(2016, 1, 1, "8088");
        int length = 3;
        long aaa[] = generator.generateArray(length);
        System.out.println("generateLongList.first:" + aaa[0]);
        System.out.println("generateLongList.last:" + aaa[length - 1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
    }

    @Test
    public void generateLongList_overflow() throws Exception {
        GenerateLongUtils generator = new GenerateLongUtilsImpl(2016, 1, 1, "8088");
        int length = 150000;
//        try {
//            long aaa[] = GeneratorUtils.generateLongList(length);
//            fail("Expected an IllegalArgumentException to be thrown");
//        }
//        catch (IllegalArgumentException ex) {
//            ex.printStackTrace();
//            assertThat(ex.getMessage(), containsString("length cannot greater than"));
//        }
        long aaa[] = generator.generateArray(length);
        System.out.println("generateLongList_overflow.first:" + aaa[0]);
        System.out.println("generateLongList_overflow.last:" + aaa[length - 1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
    }

    @Test
    public void generateLongList_2nd() throws Exception {
        GenerateLongUtils generator = new GenerateLongUtilsImpl(2016, 1, 1, "8088");
        int length = 80000;
        System.out.println("generateLongList_2nd:1st");
        long aaa[] = generator.generateArray(length);
        System.out.println("generateLongList_2nd.first:" + aaa[0]);
        System.out.println("generateLongList_2nd.last:" + aaa[length - 1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList_2nd:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
        //2nd
        length = 60000;
        System.out.println("generateLongList_2nd:2nd");
        aaa = generator.generateArray(length);
        System.out.println("generateLongList_2nd.first:" + aaa[0]);
        System.out.println("generateLongList_2nd.last:" + aaa[length - 1]);
//        for(long aa : aaa) {
//            System.out.println("generateLongList_2nd:"+aa);
//        }
        assertThat("生成个数错误", aaa.length, equalTo(length));
    }

}