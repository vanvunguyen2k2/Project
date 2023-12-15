package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private int id;
    private String username;
    private Role role;
    private String image;
    private String information;
    private String password;
    private String full_name;
    private Date date_of_birth;
    private String email;
    private Gender gender;
    private String phone_number;

}
