package com.wrpower.pjc_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PjcProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PjcProjectApplication.class, args);
    }
}
