package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.applicationForm;
import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class JobCreaterequestDto {

    @NotBlank(message = "{Job.createJob.form.name.NotBlank}")
    @Length(max = 50, message = "Tên không được vượt quá 50 ký tự")
    private String jobTitleName;


    private float salary;

    @NotBlank(message = "{Job.create.form.image.NotBlank}")
    private String image;


    private Status status;


//    private Gender gender;

    private String jobDescription;

    @NotBlank(message = "Không được để trống")
    @Length(max = 50, message = "company_name Không được vượt quá 50 ký tự")
    private String companyName;

    private String location;

    private String jobRequirements;

    private applicationForm application_way;

    private String career;



}
