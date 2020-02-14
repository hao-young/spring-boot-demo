package cn.ac.yhao.schedule.service.impl;

import cn.ac.yhao.schedule.entity.ScheduleJob;
import cn.ac.yhao.schedule.enums.JobOperateEnum;
import cn.ac.yhao.schedule.quartz.QuartzFactory;
import cn.ac.yhao.schedule.service.QuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements QuartzService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Scheduler scheduler;

    /**
     * 新增定时任务
     *
     * @param job
     */
    @Override
    public void addJob(ScheduleJob job) {

        try {
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                    .startNow().build();
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(QuartzFactory.class).withIdentity(job.getJobName()).build();

            //传入调度数据，在QuartzFactory中使用
            jobDetail.getJobDataMap().put("scheduleJob", job);
            //将jobDetail和Trigger注册到一个scheduler里，建立起两者的关联关系
            scheduler.scheduleJob(jobDetail, trigger);
            //调度作业
            scheduler.start();
        } catch(Exception e) {
            log.error("add job error!", e);
        }
    }

    /**
     * 操作定时任务
     *
     * @param jobOperateEnum
     * @param job
     */
    @Override
    public void operareJob(JobOperateEnum jobOperateEnum, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = new JobKey(job.getJobName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            throw new SchedulerException("jobdetail is null");
        }

        switch (jobOperateEnum) {
            case START:
                scheduler.resumeJob(jobKey);
                break;
            case PAUSE:
                scheduler.pauseJob(jobKey);
                break;
            case DELETE:
                scheduler.deleteJob(jobKey);
                break;
        }
    }

    /**
     * 开始所有定时任务
     */
    @Override
    public void startAllJob() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 暂停所有定时任务
     */
    @Override
    public void pauseAllJob() throws SchedulerException {
        scheduler.standby();
    }
}
