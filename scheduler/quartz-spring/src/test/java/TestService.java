import cn.kfcfr.scheduler.quartz.QuartzJob;
import cn.kfcfr.scheduler.quartz.QuartzManager;
import org.quartz.SchedulerException;

/**
 * Created by zhangqf77 on 2019/2/14.
 */
//@Service
public class TestService {
//    @Autowired
    private QuartzManager quartzManager;

    public void run() throws SchedulerException, InterruptedException {
        //新建job
        QuartzJob job = new QuartzJob();
        job.setJobName("job001");
        job.setJobGroupName("jg001");
        job.setTriggerName("trigger001");
        job.setTriggerGroupName("tg001");
        job.setCron("0/2 * * * * ?");
        job.setJobClass(TestJob.class);

        quartzManager.addJob(job);
        //启动
        startSchedule();
        //等待一段时间
        Thread.sleep(20000);
        //关闭
        stopSchedule();
    }

    protected void startSchedule() throws SchedulerException {
        quartzManager.startScheduler();
    }

    protected void stopSchedule() throws SchedulerException {
        quartzManager.clearAllJobs();
        quartzManager.stopScheduler();
    }
}
