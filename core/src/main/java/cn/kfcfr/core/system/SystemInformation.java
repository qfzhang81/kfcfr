package cn.kfcfr.core.system;

import cn.kfcfr.core.exception.WrappedException;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class SystemInformation {
    public static InetAddress getLocalNetAddress() {
        try {
            return InetAddress.getLocalHost();
        }
        catch (UnknownHostException ex) {
            throw new WrappedException(ex);
        }
    }

    public static String getLocalIp() {
        return getHostIp(getLocalNetAddress());
    }

    public static String getLocalHostName() {
        return getHostName(getLocalNetAddress());
    }

    public static String getHostIp(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        return netAddress.getHostAddress();
    }

    public static String getHostName(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        return netAddress.getHostName();
    }

    public static String getProcessId() {
        // get name representing the running Java virtual machine.
        String name = ManagementFactory.getRuntimeMXBean().getName();
//        System.out.println(name);
        // get pid
        return name.split("@")[0];
    }
}
