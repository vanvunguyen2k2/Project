package com.example.project_job_seeking.modal.dto;

import com.example.project_job_seeking.modal.Entity.Gender;
import com.example.project_job_seeking.modal.Entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SearchUserRequest extends BaseRequest{

//    TẠO RA CÁC ĐỐI TƯỢNG CẦN THIẾT KHI MUỐN TÌM KIẾM VỀ USER

    private String username;

    private Set<Role> role;

    private String full_name;

    private String email;

    private Set<Gender> gender;
}
