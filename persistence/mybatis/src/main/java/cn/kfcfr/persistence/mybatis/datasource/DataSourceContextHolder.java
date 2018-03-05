package cn.kfcfr.persistence.mybatis.datasource;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class DataSourceContextHolder {
    //线程本地环境
    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    public static void set(String value) {
        local.set(value);
    }

    public static String get() {
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}
