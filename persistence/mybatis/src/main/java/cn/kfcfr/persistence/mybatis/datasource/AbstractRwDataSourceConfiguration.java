package cn.kfcfr.persistence.mybatis.datasource;

import cn.kfcfr.persistence.common.datasource.RwDataSourceType;
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
public abstract class AbstractRwDataSourceConfiguration {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected String configLocation;

    private DataSource dsWriter;
    private DataSource[] dsReaders;
    protected RwRoundRobinRoutingDataSource dataSourceProxy;

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