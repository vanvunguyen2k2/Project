package com.example.project_job_seeking.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IFileService {

    String uploadImage(MultipartFile image) throws IOException;
    File downloadImage (String nameImage) throws IOException;

}
