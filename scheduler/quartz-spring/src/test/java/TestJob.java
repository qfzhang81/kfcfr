import cn.kfcfr.scheduler.quartz.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangqf77 on 2019/2/14.
 */
public class TestJob extends QuartzJob implements Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug(jobExecutionContext.getJobDetail().getKey().getName());
    }
}
