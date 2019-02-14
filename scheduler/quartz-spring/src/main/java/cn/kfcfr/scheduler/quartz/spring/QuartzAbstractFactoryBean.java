package cn.kfcfr.scheduler.quartz.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhangqf77 on 2018/5/22.
 */
public abstract class QuartzAbstractFactoryBean {
    private String applicationContextSchedulerContextKey = "applicationContext";
    private int startupDelay = 30;
    private boolean overwriteExistingJobs = true;
    private boolean autoStartup = false;

    public SchedulerFactoryBean getFactory(ApplicationContext applicationContext, String propertyFileName) throws IOException {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        factory.setJobFactory(jobFactory);
        //设置配置
        if(!StringUtils.isEmpty(propertyFileName)) {
            Properties a = quartzProperties(propertyFileName);
            factory.setQuartzProperties(a);
            factory.setSchedulerName(a.getProperty("org.quartz.scheduler.instanceName"));
        }
        //项目启动完成后，等待?秒后开始执行调度器初始化
        factory.setStartupDelay(startupDelay);
        //设置是否覆盖已存在的任务
        factory.setOverwriteExistingJobs(overwriteExistingJobs);
        //设置调度器是否自动运行
        factory.setAutoStartup(autoStartup);
        //设置上下文spring bean name
        factory.setApplicationContextSchedulerContextKey(applicationContextSchedulerContextKey);
        return factory;
    }

    protected Properties quartzProperties(String propertyFileName) throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(propertyFileName));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
