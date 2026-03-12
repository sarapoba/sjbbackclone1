package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UploadUtil {
    @Value("${project.upload.path}")
    private String defaultUploadPath;

    public String makeFolder() {
        // 현재 시간 정보를 받아오기    //  년, 월, 일만 받아오도록 설정
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                                        //  경로 구분자 지정
        String folderPath = date.replace("/", File.separator);

        File uploadPath = new File(defaultUploadPath + File.separator + folderPath);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
                return uploadPath.getPath();


    }
}
