package com.example.project_job_seeking.Service;

import com.example.project_job_seeking.utils.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@Service
public class FileService implements IFileService{

    private FileManager fileManager = new FileManager();
    private String linkFolder = "D:\\Avatar";


    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        String nameImage = new Date().getTime() + "." + fileManager.getFormatFile(image.getOriginalFilename());
        String path = linkFolder + "\\" + nameImage;
        fileManager.createNewMultiPartFile(path, image);

        return  path;
    }

    @Override
    public File downloadImage(String nameImage) throws IOException {
        String path = linkFolder + "\\" + nameImage;
        return  new File(path);
    }
}
