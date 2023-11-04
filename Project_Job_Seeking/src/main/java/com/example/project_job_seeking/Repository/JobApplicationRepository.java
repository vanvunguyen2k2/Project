package com.example.project_job_seeking.Repository;

import com.example.project_job_seeking.modal.Entity.JobStatus;
import com.example.project_job_seeking.modal.Entity.jobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<jobApplication, Integer> {

    List<jobApplication> findAllByJobStatusAndUser_Id(JobStatus jobStatus, int userID);

    List<jobApplication> findByUser_Id(int userID);
}
