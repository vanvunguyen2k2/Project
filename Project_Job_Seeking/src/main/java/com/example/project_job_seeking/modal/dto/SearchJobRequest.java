package com.example.project_job_seeking.modal.dto;


import com.example.project_job_seeking.modal.Entity.Status;
import com.example.project_job_seeking.modal.Entity.applicationForm;
import lombok.Data;

import java.util.Set;

@Data
public class SearchJobRequest extends BaseRequest {

    private String jobTitleName;

    //    DÙNG SET ĐỂ CHỈ LƯU ĐƯỢC 1 GIÁ TRỊ UNIQUE, KHÔNG CÓ SỰ TRÙNG LẶP

    private Set<Status> status;

    //    VỚI LƯƠNG THÌ TẠO RA  GIÁ TRỊ ĐỂ SO SÁNH VÀ KHI TÌM KIẾM SẼ TÌM KIẾM THEO GIÁ TRỊ GIỮA 2 KHOẢNG ĐÓ
    private float minSalary;
    private float maxSalary;


    //    thay vì dùng list sẽ dùng set . bởi list sẽ lưu các giá trị trùng lặp
    //    dùng set sẽ tránh được việc lặp giá trị
    //    DÙNG SET ĐỂ CHỈ LƯU ĐƯỢC 1 GIÁ TRỊ UNIQUE, KHÔNG CÓ SỰ TRÙNG LẶP
    private Set<applicationForm> application_way;

    private String companyName;

}
