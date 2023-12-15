package com.example.project_job_seeking.Service;


import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    User create(UserCreateRequestDto dto);

    Page<User> getAll(Pageable pageable);

    Page<User> search(SearchUserRequest request);

    void delete(int id);

    void deleteAll();

    User update(UserUpdateRequestDto dto);

    UserDto get_by_id(int id);

    List<User> view();


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

//    User changeUserProfile (String username, ChaneProfileDTO chaneProfileDTO);


//    User updateResetPassword(String token, String email);
}
