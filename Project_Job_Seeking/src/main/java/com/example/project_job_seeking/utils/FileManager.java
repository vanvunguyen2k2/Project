package com.example.project_job_seeking.utils;

import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public boolean isFileOrFolderExist (String path) throws Exception {
        return new File(path).exists();
    }

    public boolean isTypeFileImage (MultipartFile multipartFile) {
        return  multipartFile.getContentType().toLowerCase().contains("image");
    }

    public void createNewMultiPartFile (String path,MultipartFile multipartFile) throws IllegalStateException, IOException {

            File file = new File(path);
            multipartFile.transferTo(file);


    }

    public  String getFormatFile (String input) {
        String[] res = input.split("\\.");
        return  res[res.length -1];
    }

}
