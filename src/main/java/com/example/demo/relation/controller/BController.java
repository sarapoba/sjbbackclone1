package com.example.demo.relation.controller;


import com.example.demo.common.model.BaseResponse;
import com.example.demo.relation.service.BService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/b")
@RequiredArgsConstructor
@RestController
public class BController {
    private final BService bService;

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx){
        bService.read(idx);

        return ResponseEntity.ok(
                BaseResponse.success("성공")
        );
    }

    @GetMapping("/list")
    public ResponseEntity list() {
        bService.list();

        return ResponseEntity.ok(
                BaseResponse.success("성공")
        );
    }
}
