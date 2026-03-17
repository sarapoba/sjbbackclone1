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


    //  컨트롤러에 내가 보고 싶은 페이지 번호와 한 페이지당 몇 개씩 보고 싶다하다하는 정보를 전달해야 함
    @GetMapping("/list")
    public ResponseEntity list(
            @RequestParam(required = true, defaultValue = "0") int page,    //  페이지는 0번으로 수정
            @RequestParam(required = true, defaultValue = "5")int size) {   //  페이지당 5개 게시글  // 페이지랑 사이즈를 전달받게 수정 dto만들어서 전달해도 된다. 객체지향스럽게
        BoardDto.PageRes dto = boardService.list(page, size); // 컨트롤러에서 전달받은걸 서비스에 전달
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.ReadRes dto = boardService.read(idx);
        return ResponseEntity.ok(BaseResponse.success(dto));
    }

}

