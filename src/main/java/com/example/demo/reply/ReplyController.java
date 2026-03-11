package com.example.demo.reply;


import com.example.demo.common.model.BaseResponse;
import com.example.demo.reply.model.ReplyDto;
import com.example.demo.user.model.AuthUserDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/reply")
@RestController
public class ReplyController {
    private final ReplyService replyService;
    @PostMapping("/reg/{boardIdx}")
    public ResponseEntity reg(
            @AuthenticationPrincipal AuthUserDetails user,
            @PathVariable Long boardIdx,
            @RequestBody ReplyDto.ReplyReq dto) {

        ReplyDto.ReplyRes result = replyService.reg(user, boardIdx, dto);

        return ResponseEntity.ok(
                BaseResponse.success(result)
        );
    }
}
