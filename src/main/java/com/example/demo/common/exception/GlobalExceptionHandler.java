package com.example.demo.common.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public void handlException(Exception e){
        System.out.println("=========== 예외 처리 ===========");
        System.out.println(e.getMessage());
        System.out.println("================================");
    }
}
