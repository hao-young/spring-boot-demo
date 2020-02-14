package cn.ac.yhao.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleApplication {

    private static Logger log = LoggerFactory.getLogger(ScheduleApplication.class);
    public static void main(String[] args) {
        log.info("schedule application start!");
        SpringApplication.run(ScheduleApplication.class, args);
    }
}
