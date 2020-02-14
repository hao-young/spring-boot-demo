package cn.ac.yhao.schedule.service;

import cn.ac.yhao.schedule.entity.ScheduleJob;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 */
public interface ScheduleJobService extends IService<ScheduleJob> {

    /**
     * 新增定时任务
     * @param job
     */
    void add(ScheduleJob job);

    /**
     * 启动定时任务
     * @param id
     */
    void start(int id);

    /**
     * 暂停定时任务
     * @param id
     */
    void pause(int id);

    /**
     * 删除定时任务
     * @param id
     */
    void delete(int id);

    /**
     * 启动所有定时任务
     */
    void startAllJob();

    /**
     * 暂停所有定时任务
     */
    void pauseAllJob();
}
