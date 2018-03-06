package cn.kfcfr.persistence.mybatis.datasource.rw;

import cn.kfcfr.persistence.mybatis.datasource.AbstractDataSourceConfiguration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public abstract class AbstractRwDataSourceConfiguration extends AbstractDataSourceConfiguration {
    private DataSource dsWriter;
    private DataSource[] dsReaders;
    protected RwRoundRobinRoutingDataSource dataSourceProxy;

    protected RwRoundRobinRoutingDataSource initDataSource(DataSource dataSourceWriter, DataSource... dataSourceReaders) {
        if (dataSourceProxy == null) {
            synchronized (this) {
                if (dataSourceProxy == null) {
                    Map<Object, Object> targetDataSources = new HashMap<>();
                    targetDataSources.put(RwDataSourceType.writer.getType(), dataSourceWriter);
                    if (dataSourceReaders != null) {
                        int size = 0;
                        for (DataSource ds : dataSourceReaders) {
                            size++;
                            targetDataSources.put(RwDataSourceType.reader.getType() + "-" + size, ds);
                        }
                    }
                    RwRoundRobinRoutingDataSource dataSource = new RwRoundRobinRoutingDataSource();
                    dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
                    dataSource.setDefaultTargetDataSource(dataSourceWriter);// 默认的datasource设置为写库
                    dataSource.setReaderSize(dataSourceReaders.length);
                    this.dsWriter = dataSourceWriter;
                    this.dsReaders = dataSourceReaders;
                    this.dataSourceProxy = dataSource;
                }
            }
        }
        return this.dataSourceProxy;
    }
}
