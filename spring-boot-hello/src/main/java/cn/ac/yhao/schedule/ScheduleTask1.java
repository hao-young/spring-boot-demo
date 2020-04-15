package cn.ac.yhao.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
//@EnableScheduling
public class ScheduleTask1 {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedRate = 2000)
    public void task() {
        log.info("定时任务222 当前时间：{}", LocalDateTime.now());
    }

}
