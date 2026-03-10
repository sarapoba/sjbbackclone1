package com.example.demo.reply;


import com.example.demo.common.model.BaseResponse;
import com.example.demo.user.model.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/reply")
@RestController
public class ReplyController {
    @PostMapping("/reg/{boardIdx}")
    public ResponseEntity reg(
            @AuthenticationPrincipal AuthUserDetails user,
            @PathVariable Long boardIdx) {

        System.out.println(user);

        return ResponseEntity.ok(
                BaseResponse.success("성공")
        );
    }
}
