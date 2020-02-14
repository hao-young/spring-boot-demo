package cn.ac.yhao.schedule.quartz.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Task1 {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public void execute() {
        log.info("task 1111 running {}", LocalDateTime.now());
    }
}
