package com.example.project_job_seeking.Config.Exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class AppExceptionDto {
    private Instant timestamp;  // thời gian xảy ra lỗi
    private int status;         // trạng thái lỗi đó
    private String message;     // nguyên nhân xảy ra lỗi
    private String path;        // API xảy ra lỗi

    public AppExceptionDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }


    public AppExceptionDto(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();

    }

    public AppExceptionDto(ErrorResponseEnum errorResponseEnum) {
        this.status = errorResponseEnum.status;
        this.message = errorResponseEnum.message;
        this.timestamp = Instant.now();
    }
}
