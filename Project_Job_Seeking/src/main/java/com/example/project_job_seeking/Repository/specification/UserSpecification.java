package com.example.project_job_seeking.Repository.specification;

import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.SearchUserRequest;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> buildCondition(SearchUserRequest request){
        return Specification.where(buildConditionName(request))
                .or(buildConditionRole(request))
                .or(buildConditionFullName(request))
                .or(buildConditionEmail(request))
                .or(buildConditionGender(request));
    }


    public static Specification<User> buildConditionName(SearchUserRequest request){
        if (request.getUsername() != null && !"".equals(request.getUsername())){
            return ((root, query, cri) -> {
               return cri.like(root.get("username"), "%" + request.getUsername() + "%");
            });
        }else {
            return null;
        }
    }


    public static Specification<User> buildConditionRole(SearchUserRequest request){
        if (request.getRole() != null ){
            return ((root, query, cri) -> {
                return root.get("role").in(request.getRole());
            });
        }else {
            return null;
        }
    }
    public static Specification<User> buildConditionFullName(SearchUserRequest request){
        if (request.getFull_name() != null && !"".equals(request.getFull_name())){
            return ((root, query, cri) -> {
                return cri.like(root.get("full_name"), "%" + request.getFull_name() + "%");
            });
        }else {
            return null;
        }
    }
    public static Specification<User> buildConditionEmail(SearchUserRequest request){
        if (request.getEmail() != null && !"".equals(request.getEmail())){
            return ((root, query, cri) -> {
                return cri.like(root.get("email"), "%" + request.getEmail() + "%");
            });
        }else {
            return null;
        }
    }

    public static Specification<User> buildConditionGender(SearchUserRequest request){
        if (request.getGender() != null ){
            return ((root, query, cri) -> {
                return root.get("gender").in(request.getGender());
            });
        }else {
            return null;
        }
    }


}
