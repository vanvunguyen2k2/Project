package com.example.project_job_seeking.modal.dto;
import lombok.Data;


@Data
public class BaseRequest {

    protected int page;
    protected int page_size;
    protected String sortField;
    protected String sortType;    // ASC: sap xep tang dan   , DESC giam dan
}


