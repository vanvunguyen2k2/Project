package com.example.project_job_seeking.Config.annotation;

import com.example.project_job_seeking.Repository.UserRepository;
import com.example.project_job_seeking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIdExists implements ConstraintValidator<UserIdExisit, Integer> {


    @Autowired
    UserRepository userRepository;


    @Override
    public void initialize(UserIdExisit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

        // LOGIC kiểm tra dữ liệu xem đã có id chưa

        return userRepository.existsById(id);
    }
}
