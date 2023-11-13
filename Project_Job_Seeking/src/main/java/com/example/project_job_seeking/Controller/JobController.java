package com.example.project_job_seeking.Controller;

import com.example.project_job_seeking.Config.Exception.AppException;
import com.example.project_job_seeking.Config.Exception.ErrorResponseEnum;
import com.example.project_job_seeking.Repository.JobRepository;
import com.example.project_job_seeking.Service.JobService;
import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.Entity.JobStatus;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/job")
@CrossOrigin("*")
@Validated
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/create")
    public Job create(@RequestBody @Valid JobCreaterequestDto dto){
        return jobService.create(dto);
    }

    @PostMapping("/search")
    public Page<Job> search(@RequestBody SearchJobRequest request){
        return jobService.search(request);
    }

    @GetMapping("/get-All")
    public Page<Job> getAll(Pageable pageable){
        return jobService.getAll(pageable);
    }

    @GetMapping("/getByStatus")
    public List<Job> findByCompanyName(@RequestParam String company_name){
        return jobService.findByCompanyName(company_name);
    }

    @DeleteMapping("/delete/{id}")

    public void delete(@PathVariable(name = "id") int id){
        jobService.delete(id);
        System.out.println("Xóa thành công");
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        jobService.deleteAll();
        System.out.println("Xoa tat ca thanh cong");
    }

    @PutMapping("/update")
    public Job update(@RequestBody @Valid JobUpdateRequestDto dto){
        return jobService.update(dto);
    }

    @GetMapping("/get_by_id/{id}")
    public Job get_by_id(@PathVariable(name = "id") int id){
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            return jobService.get_by_id(id);
        }
        throw new AppException(ErrorResponseEnum.NOT_FOUND_JOB);


    }

    @GetMapping("/view")
    public List<Job> view(){
        return jobService.view();
    }


    @PostMapping("/searchfilterJob")
    public Page<Job> test (@RequestBody Search searchJobRequest, Pageable pageable) {
        return jobService.searchfilterJob(searchJobRequest, pageable);
    }

}
