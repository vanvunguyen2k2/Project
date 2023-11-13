package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.Status;
import com.example.project_job_seeking.modal.Entity.applicationForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private Set<applicationForm> application_way;
    private String locationRequest;
    private Set<Status> status;
    private int minSalary;
    private int maxSalary;
}
