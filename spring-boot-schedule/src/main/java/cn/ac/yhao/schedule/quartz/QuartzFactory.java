package cn.ac.yhao.schedule.quartz;

import cn.ac.yhao.schedule.entity.ScheduleJob;
import cn.ac.yhao.schedule.util.SpringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

public class QuartzFactory implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取调度数据
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");

        //获取对应的bean
        Object bean = SpringUtil.getBean(scheduleJob.getBeanName());

        try {
            //利用反射执行对应方法
            Method method = bean.getClass().getMethod(scheduleJob.getMethodName());
            method.invoke(bean);
        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}
