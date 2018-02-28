package cn.kfcfr.persistence.mybatis.datasource.rw;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class RwRoundRobinRoutingDataSource extends AbstractRoutingDataSource {
    protected int readerSize;
    private AtomicInteger count = new AtomicInteger(0);

    public int getReaderSize() {
        return readerSize;
    }

    public void setReaderSize(int readerSize) {
        this.readerSize = readerSize;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = RwDataSourceContextHolder.get();
        if (typeKey == null) {
            logger.error("Cannot get typeKey from DataSourceContextHolder.get() in determineCurrentLookupKey().");
            throw new NullPointerException("TypeKey cannot be null.");
        }
        String rtnKey = typeKey;
        if (typeKey.equals(RwDataSourceType.writer.getType())) {
            logger.info("Use " + rtnKey + " datasource in determineCurrentLookupKey().");
        }
        else if (typeKey.equals(RwDataSourceType.reader.getType())) {
            //读库， 轮询方式负载均衡
            int number = count.getAndAdd(1);
            int lookupKey = number % readerSize + 1;
            if (lookupKey > readerSize) lookupKey = readerSize;
            rtnKey = typeKey + "-" + lookupKey;
            logger.info("Use datasource reader" + lookupKey + " in determineCurrentLookupKey().");
        }
        return rtnKey;
    }
}