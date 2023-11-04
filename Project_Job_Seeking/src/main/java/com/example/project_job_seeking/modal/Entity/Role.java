package com.example.project_job_seeking.modal.Entity;

//import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 enum Role implements GrantedAuthority => ĐANG COI MỘT ĐÔI TƯỢNG ENUM LÀ MỘT QUYỀN TRONG SPRING SECURITY
 */
public enum Role implements GrantedAuthority {
    ADMIN, CANDIDATE, EMPLOYER;

    @Override
    public String getAuthority() {
        return name();
    }
}



