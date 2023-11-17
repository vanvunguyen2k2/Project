package com.example.project_job_seeking.Service;

import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IJobService {
    Job create(JobCreaterequestDto dto);

    Page<Job> getAll(Pageable pageable);

    Page<Job> search(SearchJobRequest request);

    void delete(int id);

    void deleteAll();

    Job update(JobUpdateRequestDto dto);

    Job get_by_id(int id);

    List<Job> findByCompanyName(String company_name);

    List<Job> view ();

    Page<Job> searchfilterJob(Search searchJobRequest , Pageable pageable);


}
