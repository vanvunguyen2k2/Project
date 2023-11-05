package com.example.project_job_seeking.Repository.specification;

import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.dto.SearchJobRequest;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job> buildCondition(SearchJobRequest request) {
        return Specification.where(buildConditionName(request))
                .and(buildConditionStatus(request))
                .and(buildConditionCompanyName(request))
                .and(buildConditionWay(request))
                .and(buildConditionLocation(request))
                .and(buildConditionSalary(request));
    }

    public static Specification<Job> buildConditionName(SearchJobRequest request) {
        if (request.getJobTitleNameRequest() != null && !"".equals(request.getJobTitleNameRequest())) {
//            Tạo điều kiện tìm kiếm với titleName
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("jobTitleName"), "%" + request.getJobTitleNameRequest() + "%");
            };

        } else {
            return null;
        }
    }

    public static Specification<Job> buildConditionLocation(SearchJobRequest request) {
        if (request.getLocationRequest() != null && !"".equals(request.getLocationRequest())) {
//            Tạo điều kiện tìm kiếm với titleName
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("location"), "%" + request.getLocationRequest().toLowerCase() + "%");
            };

        } else {
            return null;
        }
    }

    public static Specification<Job> buildConditionStatus(SearchJobRequest request) {
        if (request.getStatus() != null && request.getStatus().size() > 0) {
            //             TẠO ĐIỀU KIỆN VỚI STATUS
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
//                CÁCH 1: SỬ DỤNG EQUAL ĐỂ LẤY RA GIÁ TRỊ TRONG ROOT,
//                return cri.equal(root.get("status"), request.getStatus());

//                CÁCH 2: TẠO ĐIỀU KIỆN TÌM KIẾM VỚI STATUS.    STATUS TRẢ VỀ SẼ LÀ 1 TRONG CÁC GIÁ TRỊ MÀ MÌNH TRUYỀN VÀO
                return root.get("status").in(request.getStatus());
            };
        } else {
            return null;
        }
    }

    public static Specification<Job> buildConditionCompanyName(SearchJobRequest request) {
        if (request.getCompanyName() != null && !"".equals(request.getCompanyName())) {
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("companyName"), "%" + request.getCompanyName() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<Job> buildConditionWay(SearchJobRequest request) {
        if (request.getApplication_way() != null && request.getApplication_way().size() > 0) {

            //             TẠO ĐIỀU KIỆN VỚI WAY

            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
//                CÁCH 1: NHƯ TRÊN
//                return cri.equal(root.get("application_way"), request.getApplication_way());

//                CÁCH 2:  TẠO ĐIỀU KIỆN TÌM KIẾM VỚI WAY.    WAY TRẢ VỀ SẼ LÀ 1 TRONG CÁC GIÁ TRỊ MÀ MÌNH TRUYỀN VÀO
                return root.get("application_way").in(request.getApplication_way());
            };

        } else {
            return null;
        }

    }

    public static Specification<Job> buildConditionSalary(SearchJobRequest request) {
        if (request.getMinSalary() != 0 && request.getMaxSalary() != 0) {
//            TẠO ĐIỀU KIỆN TÌM KIẾM TRONG KHOẢNG GIÁ TỪ MIN ĐẾN MAX

            return ((root, query, cri) -> {
                return cri.between(root.get("salary"), request.getMinSalary(), request.getMaxSalary());
            });

        } else {
            return null;
        }

    }


}
