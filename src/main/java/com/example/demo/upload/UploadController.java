package com.example.demo.upload;

import com.example.demo.board.model.BoardDto;
import com.example.demo.common.model.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/upload")
@RestController
public class UploadController {
    //  의존성 주입할 객체가 프로젝트 내에 똑같은 타입이 여러개 존재할 때
    //  @Qualifier로 특정 객체를 지정하거나
    //  컴포넌트 객체에 @Primary를 달아두면 해당 객체가 주입된다.
    //  @Qualifier(value = "cloudUploadService")
    private final UploadService uploadService;

    @PostMapping("/image")
    public ResponseEntity upload(List<MultipartFile> images){
        List<String> result = uploadService.upload(images);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @PostMapping("/imageWithDto")
    public ResponseEntity upload(
            @RequestPart BoardDto.RegReq dto,
            @RequestPart List<MultipartFile> images
    ){
        System.out.println(dto.getTitle());
        System.out.println(images.get(0).getOriginalFilename());
        return ResponseEntity.ok("");
    }
}
