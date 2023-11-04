package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private int id;
    private String username;
    private Role role;
    private String full_name;

    private String userAgent;       // TÊN TRÌNH DUYỆT ĐANG SỬ DỤNG
    private String token;
}
