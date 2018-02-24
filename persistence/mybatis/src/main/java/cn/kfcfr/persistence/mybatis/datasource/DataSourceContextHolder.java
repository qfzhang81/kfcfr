package cn.kfcfr.persistence.mybatis.datasource;

import cn.kfcfr.persistence.common.datasource.DataSourceType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class DataSourceContextHolder {
    protected final Log logger = LogFactory.getLog(this.getClass());

    //线程本地环境
    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读库
     */
    public static void setReader() {
        local.set(DataSourceType.reader.getType());
        //logger.info("Datasource changed to " + DataSourceType.reader.getType());
    }

    /**
     * 读库
     */
    public static void setReader(String sourceName) {
        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
        local.set(sourceName + "-" + DataSourceType.reader.getType());
    }

    /**
     * 写库
     */
    public static void setWriter() {
        local.set(DataSourceType.writer.getType());
        //logger.info("Datasource changed to " + DataSourceType.writer.getType());
    }

    /**
     * 写库
     */
    public static void setWriter(String sourceName) {
        if (StringUtils.isBlank(sourceName)) throw new IllegalArgumentException("sourceName cannot be empty.");
        local.set(sourceName + "-" + DataSourceType.writer.getType());
    }

    public static String get() {
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}
