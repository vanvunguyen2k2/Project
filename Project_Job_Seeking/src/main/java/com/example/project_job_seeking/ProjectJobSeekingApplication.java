package com.example.project_job_seeking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectJobSeekingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectJobSeekingApplication.class, args);
    }


}
