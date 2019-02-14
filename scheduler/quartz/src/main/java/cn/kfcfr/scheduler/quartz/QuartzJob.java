package cn.kfcfr.scheduler.quartz;

import org.quartz.Job;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/5/22.
 */
public class QuartzJob implements Serializable {
    protected String jobName;
    protected String jobGroupName;
    protected String triggerName;
    protected String triggerGroupName;
    protected String cron;
    protected MisfireInstruction misfireInstruction = MisfireInstruction.do_nothing;
    protected Class<? extends Job> jobClass;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public MisfireInstruction getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(MisfireInstruction misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }
}
