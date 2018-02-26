package cn.kfcfr.persistence.mybatis.datasource;

import cn.kfcfr.persistence.common.datasource.RwDataSourceContextHolder;
import cn.kfcfr.persistence.common.datasource.RwDataSourceType;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Deprecated
public abstract class AbstractMybatisConfiguration {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected String configLocation;

    protected Integer readerSize;
    protected Map<Object, Object> targetDataSources;

    /***
     * 为targetDataSources添加数据源
     */
    protected abstract void addDataSources();

    /***
     * 一个写库，一个及以上读库
     */
    protected void initTargetDataSources() {
        if (targetDataSources == null) {
            synchronized (this) {
                if (targetDataSources == null) {
                    targetDataSources = new HashMap<>();
                    //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一致，
                    //否则切换数据源时找不到正确的数据源
                    addDataSources();
                }
            }
        }
    }

    /***
     * 得到写库
     */
    protected abstract DataSource getDataSourceWriter();

    public SqlSessionFactory sqlSessionFactory(String mapperPath) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(roundRobinDataSourceProxy());

        try {
            //设置mapper.xml文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperPath);
            sessionFactoryBean.setMapperLocations(resources);
            //设置mybatis-config.xml配置文件位置
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

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

    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //事务管理
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(roundRobinDataSourceProxy());
    }

    /**
     * 把所有数据库都放在路由中
     */
    public AbstractRoutingDataSource roundRobinDataSourceProxy() {
        initTargetDataSources();

        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {
            private AtomicInteger count = new AtomicInteger(0);

            /**
             * 这是AbstractRoutingDataSource类中的一个抽象方法，
             * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
             * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
             */
            @Override
            protected Object determineCurrentLookupKey() {
                String typeKey = RwDataSourceContextHolder.get();
                if (typeKey == null) {
                    logger.error("Cannot get typeKey from DataSourceContextHolder.get() in determineCurrentLookupKey().");
                    throw new NullPointerException("TypeKey cannot be null.");
                }
                String rtnKey = typeKey;
                if (typeKey.endsWith("-" + RwDataSourceType.writer.getType())) {
                    logger.info("Use " + rtnKey + " datasource in determineCurrentLookupKey().");
                }
                else if (typeKey.endsWith("-" + RwDataSourceType.reader.getType())) {
                    //读库， 简单负载均衡
                    int lookupKey = 1;
                    Integer size = readerSize;
                    if (size != null) {
                        int number = count.getAndAdd(1);
                        lookupKey = number % size + 1;
                        if (lookupKey > size) lookupKey = size;
                    }
                    rtnKey = typeKey + "-" + lookupKey;
                    logger.info("Use datasource reader" + lookupKey + " in determineCurrentLookupKey().");
                }
                return rtnKey;
            }
        };

        proxy.setDefaultTargetDataSource(getDataSourceWriter());//默认库
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}
