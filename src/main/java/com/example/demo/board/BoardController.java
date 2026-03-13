package com.example.demo.board;

import com.example.demo.common.model.BaseResponse;
import com.example.demo.user.model.AuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
@Tag(name="게시판 기능")
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "게시글 등록", description = "제목, 내용을 입력해서 게시글을 작성하는 기능")
    @PostMapping("/reg")
    public ResponseEntity register(
            @AuthenticationPrincipal AuthUserDetails user,
            @RequestBody BoardDto.RegReq dto) {
        BoardDto.RegRes result = boardService.register(user, dto);
        return ResponseEntity.ok(BaseResponse.success(result));
    }


    @GetMapping("/list")
    public ResponseEntity list() {
        List<BoardDto.ListRes> dto = boardService.list();
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.ReadRes dto = boardService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity update(@PathVariable Long idx, @RequestBody BoardDto.RegReq dto) {
        BoardDto.RegRes returnDto = boardService.update(idx, dto);
        return ResponseEntity.ok(BaseResponse.success(returnDto));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity update(@PathVariable Long idx) {
        boardService.delete(idx);
        return ResponseEntity.ok(BaseResponse.success("성공"));
    }
}

