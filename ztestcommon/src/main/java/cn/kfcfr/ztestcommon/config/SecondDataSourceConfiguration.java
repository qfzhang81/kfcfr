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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Configuration
@MapperScan(basePackages = "cn.kfcfr.ztestcommon.dao.db2", sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDataSourceConfiguration extends AbstractRwDataSourceConfiguration {
    //加载全局的配置文件
    @Value("${mybatis.configLocation}")
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    @Value("${jdbc.datasource.second.type}")
    protected Class<? extends DataSource> dataSourceType;

    @Bean(name = "dataSourceSecondWriter")
    @ConfigurationProperties(prefix = "jdbc.datasource.second.writer")
    public DataSource writerDataSource() {
        logger.trace("------ Second DataSource writer init ------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "dataSourceSecondReader1")
    @ConfigurationProperties(prefix = "jdbc.datasource.second.writer")
    public DataSource reader1DataSource() {
        logger.trace("------ Second DataSource reader1 init ------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Autowired
    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("secondDataSourceProxy") DataSource dataSource) {
        logger.trace("------ Second TransactionManager init ------");
        return new DataSourceTransactionManager(dataSource);
    }

    @Autowired
    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondDataSourceProxy") DataSource dataSource) throws Exception {
        logger.trace("------ Second SqlSessionTemplate init ------");
        return super.sqlSessionFactory(dataSource, "classpath:mappers/db2/**/*.xml");
    }

    @Autowired
    @Bean(name = "secondDataSourceProxy")
    protected AbstractRoutingDataSource roundRobinDataSourceProxy(@Qualifier("dataSourceSecondWriter") DataSource writer
            , @Qualifier("dataSourceSecondReader1") DataSource reader1) {
        return super.initDataSource(writer, reader1);
    }

}
