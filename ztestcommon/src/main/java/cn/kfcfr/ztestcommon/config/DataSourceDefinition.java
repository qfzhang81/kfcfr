package cn.kfcfr.ztestcommon.config;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by zhangqf77 on 2018/2/26.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Configuration
public class DataSourceDefinition {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jdbc.datasource.primary.type}")
    protected Class<? extends DataSource> primaryType;

    @Bean(name = "dataSourcePrimaryWriter")
    @Primary
    @ConfigurationProperties(prefix = "jdbc.datasource.primary.writer")
    public DataSource writerDataSource() {
        logger.trace("------ Primary DataSource writer init ------");
        return DataSourceBuilder.create().type(primaryType).build();
    }

    @Bean(name = "dataSourcePrimaryReader1")
    @ConfigurationProperties(prefix = "jdbc.datasource.primary.reader1")
    public DataSource reader1DataSource() {
        logger.trace("------ Primary DataSource reader1 init ------");
        return DataSourceBuilder.create().type(primaryType).build();
    }

    @Bean(name = "dataSourcePrimaryReader2")
    @ConfigurationProperties(prefix = "jdbc.datasource.primary.writer")
    public DataSource reader2DataSource() {
        logger.trace("------ Primary DataSource reader2 init ------");
        return DataSourceBuilder.create().type(primaryType).build();
    }
}
