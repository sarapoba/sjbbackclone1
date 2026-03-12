package com.example.demo.upload;

import com.example.demo.utils.UploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service     // 나중에 어노테이션만 때면 업그레이드 할 수 있음
public class LocalUploadService implements UploadService{
    private final UploadUtil uploadUtil;
    private String saveFile(MultipartFile file) {
        String uploadPath = uploadUtil.makeFolder();
                                                        // UUID로 랜덤한 아이디 생성   // 파일이름 받아오게
        String filePath = uploadPath + File.separator + UUID.randomUUID() + "_" +file.getOriginalFilename();

        File saveFile = new File(filePath);
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
           e.printStackTrace();
        }

        return filePath;

    }

    @Override
    public List<String> upload(List<MultipartFile> fileList) {
//        List<String> uploadPathList = new ArrayList<>();
//        for(MultipartFile file : fileList){
//            String uploadPath = saveFile(file);
//            uploadPathList.add(uploadPath);
//        }
//        return uploadPathList;

        return fileList.stream().map(this::saveFile).toList();
    }
}
