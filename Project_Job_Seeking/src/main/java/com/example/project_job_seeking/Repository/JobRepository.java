package com.example.project_job_seeking.Repository;

import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.Entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {

    List<Job> findAllByCompanyName(String name);
}
