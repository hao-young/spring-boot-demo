package cn.ac.yhao.schedule.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     *任务分组
     */
    private String jobGroup;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 服务名称
     */
    private String beanName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 状态 START:启动, PAUSE:暂停, STOP:停止
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}