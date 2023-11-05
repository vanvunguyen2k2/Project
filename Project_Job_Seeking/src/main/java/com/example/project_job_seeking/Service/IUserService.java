package com.example.project_job_seeking.Service;


import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.BaseRequest;
import com.example.project_job_seeking.modal.dto.SearchUserRequest;
import com.example.project_job_seeking.modal.dto.UserCreateRequestDto;
import com.example.project_job_seeking.modal.dto.UserUpdateRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    User create(UserCreateRequestDto dto);

    List<User> getAll();

    Page<User> search(SearchUserRequest request);

    void delete(int id);

    void deleteAll();

    User update(UserUpdateRequestDto dto);

    User get_by_id(int id);





//    User updateResetPassword(String token, String email);
}
