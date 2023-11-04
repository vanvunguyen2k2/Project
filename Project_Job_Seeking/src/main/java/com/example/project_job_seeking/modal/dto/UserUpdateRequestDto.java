package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserUpdateRequestDto {
    private int id;

    @NotBlank(message = " Username Không được để trống")
    @Length(max = 50, message = "Không được vượt quá 50 ký tự")
    private String username;

    @NotBlank(message = "Phone_number Không được để trống")
    private String phone_number;

    @NotBlank(message = "Password Không được để trống")
    private String password;

    private String information;

    @Column(name = "image", nullable = false)
    private String image;

    private Gender gender;

    @NotBlank(message = "full_name Không được để trống")
    @Length(max = 50, message = "Không được vượt quá 50 ký tự")
    private String full_name;

    @NotBlank(message = "email Không được để trống")
    private String email;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date_of_birth;


    private Role role;
}
