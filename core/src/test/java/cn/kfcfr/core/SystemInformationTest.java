package cn.kfcfr.core;

import org.junit.Test;

import java.net.InetAddress;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by zhangqf on 2017/6/10.
 */
public class SystemInformationTest {
    @Test
    public void getLocalInetAddress() throws Exception {
        InetAddress rst = SystemInformation.getLocalNetAddress();
        System.out.println(rst);
        assertThat("没有获取本机地址", rst, is(notNullValue()));
    }

    @Test
    public void getLocalIp() throws Exception {
        String rst = SystemInformation.getLocalIp();
        System.out.println(rst);
        assertThat("没有获取本机IP", rst, is(notNullValue()));
    }

    @Test
    public void getLocalHostName() throws Exception {
        String rst = SystemInformation.getLocalHostName();
        System.out.println(rst);
        assertThat("没有获取本机机器名", rst, is(notNullValue()));
    }

}