package cn.kfcfr.persistence.mybatis.datasource.rw;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class RwDataSourceContextHolder {
    private static final String SPLIT_CHAR = "^^";

    //线程本地环境
    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    public static void set(String value) {
        local.set(value);
    }

    public static void set(String sourceName, String value) {
        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
        local.set(sourceName + SPLIT_CHAR + value);
    }

//    /**
//     * 读库
//     */
//    public static void setReader() {
//        local.set(RwDataSourceType.reader.getType());
//    }

//    /**
//     * 读库
//     */
//    public static void setReader(String sourceName) {
//        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
//        local.set(sourceName + "-" + RwDataSourceType.reader.getType());
//    }
//
//    /**
//     * 写库
//     */
//    public static void setWriter() {
//        local.set(RwDataSourceType.writer.getType());
//    }
//
//    /**
//     * 写库
//     */
//    public static void setWriter(String sourceName) {
//        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
//        local.set(sourceName + "-" + RwDataSourceType.writer.getType());
//    }

    private static String getSplit(int index) {
        String str = local.get();
        if (StringUtils.isEmpty(str)) return "";
        String[] arr = str.split(SPLIT_CHAR);
        if (index >= arr.length) return null;
        return arr[index];
    }

    public static String get() {
        return local.get();
    }

    public static String getSourceName() {
        return getSplit(0);
    }

    public static String getValue() {
        return getSplit(1);
    }

    public static void clear() {
        local.remove();
    }
}
