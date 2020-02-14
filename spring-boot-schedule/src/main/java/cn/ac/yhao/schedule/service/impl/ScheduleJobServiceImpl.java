package cn.ac.yhao.schedule.service.impl;

import cn.ac.yhao.schedule.entity.ScheduleJob;
import cn.ac.yhao.schedule.enums.JobOperateEnum;
import cn.ac.yhao.schedule.mapper.ScheduleJobMapper;
import cn.ac.yhao.schedule.service.QuartzService;
import cn.ac.yhao.schedule.service.ScheduleJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuartzService quartzService;

    @PostConstruct
    private void init() {
        log.info("schedule init start!");
        List<ScheduleJob> scheduleJobs = this.list();
        if (scheduleJobs != null) {
            log.info("schedule list size:{}", scheduleJobs.size());
            scheduleJobs.forEach((job)->{
                if (job.getStatus().equals(JobOperateEnum.START.value())) {
                    quartzService.addJob(job);
                }
            });
        }
    }

    /**
     * 新增定时任务
     *
     * @param job
     */
    @Override
    public void add(ScheduleJob job) {
        //TODO 数据校验
        this.save(job);

        quartzService.addJob(job);

    }

    /**
     * 启动定时任务
     *
     * @param id
     */
    @Override
    public void start(int id) {
        //数据校验
        ScheduleJob scheduleJob = this.getById(id);
        scheduleJob.setStatus(JobOperateEnum.START.value());
        this.updateById(scheduleJob);

        //执行job
        try {
            quartzService.operareJob(JobOperateEnum.START, scheduleJob);
        } catch(SchedulerException e) {
            log.error("start schedule error!", e);
        }

    }

    /**
     * 暂停定时任务
     *
     * @param id
     */
    @Override
    public void pause(int id) {
        //数据校验
        ScheduleJob scheduleJob = this.getById(id);
        scheduleJob.setStatus(JobOperateEnum.PAUSE.value());
        this.updateById(scheduleJob);

        //执行job
        try {
            quartzService.operareJob(JobOperateEnum.PAUSE, scheduleJob);
        } catch(SchedulerException e) {
            log.error("pause job error!", e);
        }
    }

    /**
     * 删除定时任务
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        //数据校验
        ScheduleJob scheduleJob = this.getById(id);
        this.removeById(id);

        //执行job
        try {
            quartzService.operareJob(JobOperateEnum.DELETE, scheduleJob);
        } catch(SchedulerException e) {
            log.error("delete schedule error!", e);
        }
    }

    /**
     * 启动所有定时任务
     */
    @Override
    public void startAllJob() {
        //此处省去数据验证
        ScheduleJob job = new ScheduleJob();
        job.setStatus(JobOperateEnum.START.value());
        this.update(job, new QueryWrapper<>());

        //执行job
        try {
            quartzService.startAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停所有定时任务
     */
    @Override
    public void pauseAllJob() {
        //此处省去数据验证
        ScheduleJob job = new ScheduleJob();
        job.setStatus(JobOperateEnum.PAUSE.value());
        this.update(job, new QueryWrapper<>());

        //执行job
        try {
            quartzService.pauseAllJob();
        } catch (SchedulerException e) {
            log.error("pause all job error!", e);
        }
    }
}
