package com.example.project_job_seeking.Service;

import com.example.project_job_seeking.Repository.JobRepository;
import com.example.project_job_seeking.Repository.specification.JobSpecification;
import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.BaseRequest;
import com.example.project_job_seeking.modal.dto.JobCreaterequestDto;
import com.example.project_job_seeking.modal.dto.JobUpdateRequestDto;
import com.example.project_job_seeking.modal.dto.SearchJobRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class JobService implements IJobService {

    @Autowired
    private final JobRepository jobRepository;


    @Override
    public Job create(JobCreaterequestDto dto) {
        List<Job> jobList = new ArrayList<>();
        Job entity = new Job();
        BeanUtils.copyProperties(dto, entity);
        jobList.add(entity);
        return jobRepository.save(entity);
    }

    @Override
    public Page<Job> getAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }


//    --------------- PHÂN TRANG -----------------
    @Override
    public Page<Job> search(SearchJobRequest request) {
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())) {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getPage_size(), Sort.by(request.getSortField()).descending());

        } else {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getPage_size(), Sort.by(request.getSortField()).ascending());
        }
//        PageRequest pageRequest = PageRequest.of(baseRequest.getPage() , baseRequest.getPage_size());
        Specification<Job> condition = JobSpecification.buildCondition(request);
        System.out.println(condition);
        return jobRepository.findAll(condition, pageRequest);
    }

    @Override
    public void delete(int id) {
        jobRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        jobRepository.deleteAll();
    }


    //    UPDATE
    @Override
    public Job update(JobUpdateRequestDto dto) {
        Job entity = new Job();
        BeanUtils.copyProperties(dto, entity);
        return jobRepository.save(entity);
    }

    @Override
    public Job get_by_id(@PathVariable(name = "id") int id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            return jobOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Job> findByCompanyName(String company_name) {
        if (company_name != null) {
            return jobRepository.findAllByCompanyName(company_name);
        } else {
            return null;
        }


    }
}
