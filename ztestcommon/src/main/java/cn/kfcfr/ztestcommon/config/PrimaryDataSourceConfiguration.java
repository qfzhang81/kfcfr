package cn.kfcfr.ztestcommon.config;

import cn.kfcfr.persistence.mybatis.datasource.AbstractRwDataSourceConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Configuration
@MapperScan(basePackages = "cn.kfcfr.ztestcommon.dao", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDataSourceConfiguration extends AbstractRwDataSourceConfiguration {
    //加载全局的配置文件
    @Value("${mybatis.configLocation}")
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    @Value("${jdbc.datasource.primary.type}")
    protected Class<? extends DataSource> dataSourceType;

    @Bean(name = "dataSourcePrimaryWriter")
    @Primary
    @ConfigurationProperties(prefix = "jdbc.datasource.primary.writer")
    public DataSource writerDataSource() {
        logger.trace("------ Primary DataSource writer init ------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "dataSourcePrimaryReader1")
    @ConfigurationProperties(prefix = "jdbc.datasource.primary.reader1")
    public DataSource reader1DataSource() {
        logger.trace("------ Primary DataSource reader1 init ------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "dataSourcePrimaryReader2")
    @ConfigurationProperties(prefix = "jdbc.datasource.primary.writer")
    public DataSource reader2DataSource() {
        logger.trace("------ Primary DataSource reader2 init ------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Autowired
    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("primaryDataSourceProxy") DataSource dataSource) {
        logger.trace("------ Primary TransactionManager init ------");
        return new DataSourceTransactionManager(dataSource);
    }

    @Autowired
    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSourceProxy") DataSource dataSource) throws Exception {
        logger.trace("------ Primary SqlSessionTemplate init ------");
        return super.sqlSessionFactory(dataSource, "classpath:mappers/**/*.xml");
    }

    @Autowired
    @Bean(name = "primaryDataSourceProxy")
    protected AbstractRoutingDataSource roundRobinDataSourceProxy(@Qualifier("dataSourcePrimaryWriter") DataSource writer
            , @Qualifier("dataSourcePrimaryReader1") DataSource reader1
            , @Qualifier("dataSourcePrimaryReader2") DataSource reader2) {
        return super.initDataSource(writer, reader1, reader2);
    }

}
