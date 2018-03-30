package cn.kfcfr.persistence.mybatis.datasource.rw;

import cn.kfcfr.persistence.mybatis.datasource.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class RwRoundRobinRoutingDataSource extends AbstractRoutingDataSource {
    protected int readerSize;
    protected String WriterPrefix = "writer";
    protected String ReaderPrefix = "reader-";
    private AtomicInteger count = new AtomicInteger(0);

    public int getReaderSize() {
        return readerSize;
    }

    public void setReaderSize(int readerSize) {
        this.readerSize = readerSize;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.get();
        if (typeKey == null) {
            logger.error("Cannot get typeKey from DataSourceContextHolder.get() in determineCurrentLookupKey().");
            throw new NullPointerException("TypeKey cannot be null.");
        }
        String rtnKey = WriterPrefix;
        if (readerSize > 0 && !Boolean.parseBoolean(typeKey)) {
            //读库， 轮询方式负载均衡
            int number = count.getAndAdd(1);
            int lookupKey = number % readerSize + 1;
            if (lookupKey > readerSize) lookupKey = readerSize;
            rtnKey = ReaderPrefix + lookupKey;
        }
        logger.info("Use datasource " + rtnKey + " in determineCurrentLookupKey().");
        return rtnKey;
    }
}
