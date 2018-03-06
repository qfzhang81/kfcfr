package cn.kfcfr.persistence.mybatis.datasource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public abstract class AbstractDataSourceConfiguration {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected String configLocation;

    public abstract void setConfigLocation(String configLocation);

    public SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String mapperPath) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        try {
            //设置mapper.xml文件所在位置
            if (mapperPath != null) {
                Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperPath);
                sessionFactoryBean.setMapperLocations(resources);
            }
            //设置mybatis-config.xml配置文件位置
            if (configLocation != null) {
                sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
            }

            return sessionFactoryBean.getObject();
        }
        catch (IOException ex) {
            logger.error("Resolver mapper*xml is error", ex);
            throw ex;
        }
        catch (Exception ex) {
            logger.error("SqlSessionFactoryBean create error", ex);
            throw ex;
        }
    }

    public DataSourceTransactionManager createDataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
