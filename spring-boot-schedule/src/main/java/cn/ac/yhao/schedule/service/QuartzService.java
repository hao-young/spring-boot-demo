package cn.ac.yhao.schedule.service;

import cn.ac.yhao.schedule.entity.ScheduleJob;
import cn.ac.yhao.schedule.enums.JobOperateEnum;
import org.quartz.SchedulerException;

public interface QuartzService {

    /**
     * 新增定时任务
     * @param job
     */
    void addJob(ScheduleJob job);

    /**
     * 操作定时任务
     * @param jobOperateEnum
     * @param job
     */
    void operareJob(JobOperateEnum jobOperateEnum, ScheduleJob job) throws SchedulerException;

    /**
     * 开始所有定时任务
     */
    void startAllJob() throws SchedulerException;

    /**
     * 暂停所有定时任务
     */
    void pauseAllJob() throws SchedulerException;
}
