package cn.ac.yhao.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class ScheduleTask1 {

    @Scheduled(fixedRate = 2000)
    public void task() {
        System.out.println("定时任务222 当前时间：" + LocalDateTime.now());
    }

}
