package cn.kfcfr.ztestcommon.config;

import cn.kfcfr.persistence.mybatis.datasource.single.AbstractSingleDataSourceConfiguration;
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

import javax.sql.DataSource;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Configuration
@MapperScan(basePackages = "cn.kfcfr.ztestcommon.dao.db2", sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDataSourceConfiguration extends AbstractSingleDataSourceConfiguration {
    //加载全局的配置文件
    @Value("${mybatis.configLocation}")
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    @Value("${jdbc.datasource.second.type}")
    protected Class<? extends DataSource> dataSourceType;

    @Bean(name = "dataSourceSecond")
    @ConfigurationProperties(prefix = "jdbc.datasource.second.writer")
    public DataSource dataSource() {
        logger.trace("------ Second DataSource writer init ------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Autowired
    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSourceSecond") DataSource dataSource) {
        logger.trace("------ Second TransactionManager init ------");
        return createDataSourceTransactionManager(dataSource);
    }

    @Autowired
    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceSecond") DataSource dataSource) throws Exception {
        logger.trace("------ Second SqlSessionTemplate init ------");
        return createSqlSessionFactory(dataSource, "classpath:mappers/db2/**/*.xml");
    }

}
