package com.example.project_job_seeking.modal.dto;


import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.Entity.Role;
import com.example.project_job_seeking.modal.Entity.jobApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    private String username;

    private Role role;


    private String image;

    private String phone_number;

    private String password;

    private String information;

    private String full_name;

    private String email;


    private Date date_of_birth;

    private Gender gender;

    private Set<Job> appliedJob;

}
