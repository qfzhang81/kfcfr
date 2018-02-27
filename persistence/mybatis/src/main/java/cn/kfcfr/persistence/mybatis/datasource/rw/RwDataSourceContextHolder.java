package cn.kfcfr.persistence.mybatis.datasource.rw;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class RwDataSourceContextHolder {

    //线程本地环境
    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读库
     */
    public static void setReader() {
        local.set(RwDataSourceType.reader.getType());
    }

    /**
     * 读库
     */
    public static void setReader(String sourceName) {
        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
        local.set(sourceName + "-" + RwDataSourceType.reader.getType());
    }

    /**
     * 写库
     */
    public static void setWriter() {
        local.set(RwDataSourceType.writer.getType());
    }

    /**
     * 写库
     */
    public static void setWriter(String sourceName) {
        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
        local.set(sourceName + "-" + RwDataSourceType.writer.getType());
    }

    public static String get() {
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}
