package com.example.demo.relation.controller;


import com.example.demo.common.model.BaseResponse;
import com.example.demo.relation.model.ADto;
import com.example.demo.relation.service.AService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/a")
@RequiredArgsConstructor
@RestController
public class AController {
    private final AService aService;

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        ADto.ARes result = aService.read(idx);

        return ResponseEntity.ok(
                BaseResponse.success(result)
        );
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        aService.list();

        return ResponseEntity.ok(
                BaseResponse.success("성공")
        );
    }
}
