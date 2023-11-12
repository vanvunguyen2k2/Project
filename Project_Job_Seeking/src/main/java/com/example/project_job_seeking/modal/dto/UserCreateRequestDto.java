package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.Config.annotation.NotAdmin;
import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "UserName Không được để trống")
    @Length(max = 50, message = "Không được vượt quá 50 ký tự")
    @NotAdmin
    private String username;

    @NotBlank(message = "Phone Không được để trống")
    private String phone_number;

    @NotBlank(message = "Password Không được để trống")
    private String password;

    @Column(name = "image", nullable = false)
    private String image;

    private String information;

    @NotBlank(message = "Full_name Không được để trống")
    @Length(max = 50, message = "Không được vượt quá 50 ký tự")
    private String full_name;

    @NotBlank(message = "Email Không được để trống")
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date_of_birth;

    private Gender gender;


//    @Pattern(regexp = "ADMIN | CANDIDATE | EMPLOYER", message = "must be default")
    private Role role;
}
