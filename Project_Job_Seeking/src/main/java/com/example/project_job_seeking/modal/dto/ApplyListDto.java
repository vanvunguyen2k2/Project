package com.example.project_job_seeking.modal.dto;


import com.example.project_job_seeking.modal.Entity.JobStatus;
import com.example.project_job_seeking.modal.Entity.User;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyListDto {

    private int id;
    private String userUsername;
    private String jobCompanyName;
//    private String jobStatus;


}
