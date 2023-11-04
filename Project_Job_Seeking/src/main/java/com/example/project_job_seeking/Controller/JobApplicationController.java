package com.example.project_job_seeking.Controller;

import com.example.project_job_seeking.Service.JobApplicationService;
import com.example.project_job_seeking.modal.Entity.JobStatus;
import com.example.project_job_seeking.modal.Entity.jobApplication;
import com.example.project_job_seeking.modal.dto.JobApplicationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job_management")
@CrossOrigin("*")
public class JobApplicationController {

    @Autowired
    private JobApplicationService job_management_service;

    @PostMapping("/create")
    public jobApplication create(@RequestBody JobApplicationRequestDto dto){
        return job_management_service.create(dto);
    }

    @PostMapping("/getAll")
    public List<jobApplication> getAll(){
        return job_management_service.getAll();
    }

    @PostMapping("/delete/{id}")
    public jobApplication cancelJobManagement(@PathVariable int id){
        return job_management_service.cancelJobManagament(id);
    }

    @PostMapping("/deleteAll")
    public void deleteAll() {
        job_management_service.deleteAll();
        System.out.println("Xoa tat ca thanh cong");
    }

    @PostMapping("/get-by-jobstatus")
    public List<jobApplication> findByJobStatus(@RequestParam(required = false) JobStatus jobStatus,@RequestParam int userID){
        return job_management_service.findByJobStatus(jobStatus, userID);
    }

    @PostMapping("/approvedJob/{jobManaId}")
    public jobApplication approvedJob(@PathVariable int jobManaId) {
        return job_management_service.approvedJob(jobManaId);

    }

}
