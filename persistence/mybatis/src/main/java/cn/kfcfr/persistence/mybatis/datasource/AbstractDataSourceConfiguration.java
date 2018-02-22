package cn.kfcfr.persistence.mybatis.datasource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public abstract class AbstractDataSourceConfiguration {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected String configLocation;

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperPath) throws Exception {
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

    protected RoundRobinRoutingDataSource initDataSource(DataSource dataSourceWriter, DataSource... dataSourceReaders) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.writer.getType(), dataSourceWriter);
        if (dataSourceReaders != null) {
            int size = 0;
            for (DataSource ds : dataSourceReaders) {
                size++;
                targetDataSources.put(DataSourceType.reader.getType() + "-" + size, ds);
            }
        }
        RoundRobinRoutingDataSource dataSource = new RoundRobinRoutingDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(dataSourceWriter);// 默认的datasource设置为写库
        return dataSource;
    }
}
