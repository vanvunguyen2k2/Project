package com.example.project_job_seeking.Repository.specification;

import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.dto.Search;
import com.example.project_job_seeking.modal.dto.SearchJobRequest;
import org.springframework.data.jpa.domain.Specification;

public class TestSpificationJob {

    public static Specification<Job> buildRequest (Search searchJobRequest) {
        return Specification.where(ConditionWayRequest(searchJobRequest))
                .and(ConditionLocationRequest(searchJobRequest)).and(ConditionStatusRequest(searchJobRequest));
    }


    public static Specification<Job> ConditionLocationRequest(Search  searchJobRequest){

        if (searchJobRequest.getLocationRequest() != null && !"".equals(searchJobRequest.getLocationRequest())) {
            return ((root, query, criteriaBuilder) -> {
                return criteriaBuilder.like(root.get("location"), "%" + searchJobRequest.getLocationRequest() +"%");
            });

        }else {
            return null;
        }
    }

    public static Specification<Job> ConditionStatusRequest(Search  searchJobRequest){

        if (searchJobRequest.getStatus() != null && searchJobRequest.getStatus().size() > 0) {
            return ((root, query, criteriaBuilder) -> {
                return root.get("status").in(searchJobRequest.getStatus());
            });

        }else {
            return null;
        }
    }

    public static Specification<Job> ConditionWayRequest(Search  searchJobRequest){

        if (searchJobRequest.getApplication_way() != null && searchJobRequest.getApplication_way().size() > 0) {
            return ((root, query, criteriaBuilder) -> {
                return root.get("application_way").in(searchJobRequest.getApplication_way());
            });

        }else {
            return null;
        }
    }

}
