package com.online.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:directory.properties")
public class OnlineCinemaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineCinemaBackendApplication.class, args);
    }

}
