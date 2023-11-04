package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.Entity.JobStatus;
import lombok.Data;

@Data
public class JobApplicationRequestDto {
    private int user_id;
    private int job_id;
//    private JobStatus jobStatus;
}
