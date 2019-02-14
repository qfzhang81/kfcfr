package cn.kfcfr.scheduler.quartz;

import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.core.exception.WrappedException;
import org.quartz.*;

import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/5/22.
 */
public class QuartzManager {
    protected Scheduler scheduler;

    public QuartzManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void startScheduler() {
        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        }
        catch (SchedulerException ex) {
            throw new WrappedException("Start scheduler failed.", ex);
        }
    }

    public void stopScheduler() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(true);
            }
        }
        catch (SchedulerException ex) {
            throw new WrappedException("Stop scheduler failed.", ex);
        }
    }

    public void pauseAllJobs() {
        try {
            scheduler.pauseAll();
        }
        catch (SchedulerException ex) {
            throw new WrappedException("Pause all jobs failed.", ex);
        }
    }

    public void resumeAllJobs() {
        try {
            scheduler.resumeAll();
        }
        catch (SchedulerException ex) {
            throw new WrappedException("Resume all jobs failed.", ex);
        }
    }

    public void clearAllJobs() {
        try {
            scheduler.clear();
        }
        catch (SchedulerException ex) {
            throw new WrappedException("Clear all jobs failed.", ex);
        }
    }

    public void pauseJob(QuartzJob job) {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroupName());
        try {
            scheduler.pauseJob(jobKey);
        }
        catch (SchedulerException ex) {
            throw new WrappedException(MessageFormat.format("Pause job '{0}' in '{1}' failed.", job.getJobName(), job.getJobGroupName()), ex);
        }
    }

    public void resumeJob(QuartzJob job) {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroupName());
        try {
            scheduler.resumeJob(jobKey);
        }
        catch (SchedulerException ex) {
            throw new WrappedException(MessageFormat.format("Resume job '{0}' in '{1}' failed.", job.getJobName(), job.getJobGroupName()), ex);
        }
    }

    public void addJob(QuartzJob job) {
        // 1、创建一个JobDetail实例，指定Quartz
        JobDetail jobDetail = JobBuilder.newJob(job.getJobClass()) // 任务执行类
                .withIdentity(job.getJobName(), job.getJobGroupName()) // 任务名，任务组
                .build();
        CronScheduleBuilder builder = createCronBuilder(job);
        // 2、创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getTriggerName(), job.getTriggerGroupName()).startNow()
                .withSchedule(builder)
                .build();
        // 3、调度执行
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (SchedulerException ex) {
            throw new WrappedException(MessageFormat.format("Add job '{0}' failed.", JsonConvert.toJsonString(job)), ex);
        }
    }

    public void modifyJob(QuartzJob job) {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroupName());
        CronScheduleBuilder builder = createCronBuilder(job);
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(builder)
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        catch (SchedulerException ex) {
            throw new WrappedException(MessageFormat.format("Modify job '{0}' failed.", JsonConvert.toJsonString(job)), ex);
        }
    }

    public void deleteJob(QuartzJob job) {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroupName());
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroupName());
        try {
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(jobKey);// 删除任务
        }
        catch (SchedulerException ex) {
            throw new WrappedException(MessageFormat.format("Delete job '{0}' failed.", JsonConvert.toJsonString(job)), ex);
        }
    }

    protected CronScheduleBuilder createCronBuilder(QuartzJob job) {
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(job.getCron());
        if (job.getMisfireInstruction() != null && job.getMisfireInstruction().getCode() == MisfireInstruction.fire_once_now.getCode()) {
            builder = builder.withMisfireHandlingInstructionFireAndProceed();
        }
        else if (job.getMisfireInstruction() != null && job.getMisfireInstruction().getCode() == MisfireInstruction.ignore_misfires.getCode()) {
            builder = builder.withMisfireHandlingInstructionIgnoreMisfires();
        }
        else {
            // 超时后等待下一次触发时间
            builder = builder.withMisfireHandlingInstructionDoNothing();
        }
        return builder;
    }
}
