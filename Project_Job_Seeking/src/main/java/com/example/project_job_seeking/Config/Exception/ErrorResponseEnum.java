package com.example.project_job_seeking.Config.Exception;

public enum ErrorResponseEnum {
    NOT_FOUND_JOB(404, "KHÔNG TÌM THẤY JOB"),
    NOT_FOUND_USER(404, "KHÔNG TÌM THẤY NGƯỜI DÙNG"),
    USERNAME_NOT_EXIST(401, "SAI USERNAME"),
    PASWORD_WRONG(404, "SAI MẬT KHẨU"),
    NOT_FOUND_JOB_APPLY(404, "KHÔNG TÌM THẤY CÔNG VIỆC NÀO APPLY GẦN ĐÂY"),
    USER_NAME_EXISTSED(404, "Username da ton tai"),
    EMAIL_NOT_EXISTED(404, "KHÔNG TÌM THẤY EMAIL BẠN VỪA NHẬP"),
    FORBIDDEN(403, "KHÔNG ĐƯỢC PHÉP XÓA USER NÀY"),
    FORBIDDEN_USER(403, "BẠN CHƯA KÍCH HOẠT TÀI KHOẢN"),

    Not_FOUND(404, "KHÔNG TÌM THẤY JOB hoac KHÔNG TÌM THẤY NGƯỜI DÙNG");

    public final int status;
    public final String message;

    ErrorResponseEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
