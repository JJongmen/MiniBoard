package com.jyp.miniboard;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class MiniBoardApplication {

    public static final String KST = "Asia/Seoul";

    @PostConstruct
    public void started() {
        // timezone KST 설정
        TimeZone.setDefault(TimeZone.getTimeZone(KST));
    }

    public static void main(String[] args) {
        SpringApplication.run(MiniBoardApplication.class, args);
    }

}
