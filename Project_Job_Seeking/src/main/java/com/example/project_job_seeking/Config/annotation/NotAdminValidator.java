package com.example.project_job_seeking.Config.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * ConstraintValidator<NotAdmin, Integer>
 *     trong do
 *           A : annotation vua tao
 *           T : kieu du lieu ma minh muon validated
 */

public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {
    @Override
    public void initialize(NotAdmin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !username.contains("ADMIN");
    }
}
