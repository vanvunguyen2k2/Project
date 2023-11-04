package com.example.project_job_seeking.modal.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name = "create_by")
    protected String create_by;

    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    protected Date create_date;

    @Column(name = "update_by")
    protected String update_by;

    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

    protected Date update_date;


//    HÀM NÀY SẼ ĐƯỢC GỌI KHI ENTITY ĐƯỢC THÊM MỚI
    @PrePersist
    public void onPrepersist(){
        this.create_by = "Nguyễn Văn Vũ";
        this.create_date = new Date();
    }


//    HÀM NÀY SẼ ĐƯỢC GỌI KHI ENTITY ĐƯỢC UPDATE
    @PreUpdate
    public void onPreUpdate(){
        onPrepersist();
        this.update_by = "Nguyễn Văn Vũ";
        this.update_date = new Date();
    }
}
