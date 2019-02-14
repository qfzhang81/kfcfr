import cn.kfcfr.scheduler.quartz.QuartzManager;
import cn.kfcfr.scheduler.quartz.spring.QuartzAbstractFactoryBean;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/**
 * Created by zhangqf77 on 2019/2/14.
 */
@Configuration
public class TestQuartzConfiguration extends QuartzAbstractFactoryBean {

    @Value("${spring.profiles.active}")
    private String profileName;

    @Bean(destroyMethod = "destroy", autowire = Autowire.NO)
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) throws IOException {
        SchedulerFactoryBean factory = this.getFactory(applicationContext, "/config/quartz-" + profileName + ".properties");
        return factory;
    }

    @Bean
    public QuartzManager quartzManager(Scheduler scheduler) throws IOException {
        QuartzManager manager = new QuartzManager(scheduler);
        return manager;
    }

}
