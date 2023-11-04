package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.applicationForm;
import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Status;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class JobUpdateRequestDto {
    private int id;

    @NotBlank(message = "Không được để trống")
    @Length(max = 50, message = "Không được vượt quá 50 ký tự")
    private String jobTitleName;

//    @NotEmpty(message = "Không được để trống")
    private float salary;


    private String image;

//    @NotBlank(message = "Không được để trống")
    private Status status;

//    @NotBlank(message = "Không được để trống")
//    private Gender gender;
    private String jobDescription;

    @NotBlank(message = "Không được để trống")
    @Length(max = 50, message = "Không được vượt quá 50 ký tự")
    private String companyName;
    private String location;
    private String jobRequirements;

//    @NotBlank(message = "Không được để trống")
    private applicationForm application_way;
}
