package cn.ac.yhao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationTest {

    @Test
    public void contextLoads() {
        System.err.println("Hello Spring Boot 2.X!");
    }

}