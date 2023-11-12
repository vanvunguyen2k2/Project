package com.example.project_job_seeking.Service;

import com.example.project_job_seeking.modal.Entity.JobStatus;
import com.example.project_job_seeking.modal.Entity.jobApplication;
import com.example.project_job_seeking.modal.dto.JobApplicationRequestDto;

import java.util.List;


public interface IJobApplicationService {

    jobApplication create(JobApplicationRequestDto dto);

    List<jobApplication> getAll();

    void deleteAll();

    jobApplication cancelJobManagament(int id);

    List<jobApplication> findByJobStatus(JobStatus jobStatus, int userID);

    jobApplication approvedJob(int jobManaId);


}
