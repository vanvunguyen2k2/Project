package com.example.project_job_seeking.Config.Exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class AppException extends  RuntimeException{

    private Instant timestamp;  // thời gian xảy ra lỗi
    private int status;         // trạng thái lỗi đó
    private String message;     // nguyên nhân xảy ra lỗi
    private String path;        // API xảy ra lỗi

    public AppException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }


    public AppException(ErrorResponseEnum errorResponseEnum) {
        this.status = errorResponseEnum.status;
        this.message = errorResponseEnum.message;
        this.timestamp = Instant.now();
    }

}
