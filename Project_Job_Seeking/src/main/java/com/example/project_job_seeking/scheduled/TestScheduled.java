package com.example.project_job_seeking.scheduled;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TestScheduled {

    //    @Scheduled(cron = "0 0/1 * * * *")
    public void Test () {

        System.out.println("run " + Instant.now());

    }
}
