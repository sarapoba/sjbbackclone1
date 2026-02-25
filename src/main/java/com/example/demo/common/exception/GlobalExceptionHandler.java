package com.example.demo.common.exception;


import com.example.demo.common.model.BaseResponse;
import com.example.demo.common.model.BaseResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity handleException(BaseException e){
        BaseResponseStatus status = e.getStatus();
        int errorCode = status.getCode();
        int statusCode = statusCodeMapper(errorCode);
        BaseResponse response = BaseResponse.fail(status);

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }

    private int statusCodeMapper(int errorCode){
        if(errorCode > 3000){
            return 400;
        } else if(errorCode >= 5000){
            return 500;
        }
        return 400;
    }
}
