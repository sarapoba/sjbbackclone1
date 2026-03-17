package com.example.demo.board;

import com.example.demo.board.model.Board;
import com.example.demo.user.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import com.example.demo.board.model.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDto.RegRes register(AuthUserDetails user, BoardDto.RegReq dto) {

        Board entity = boardRepository.save(dto.toEntity(user));

        return BoardDto.RegRes.from(entity);
    }

    public BoardDto.PageRes list(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);    // Pageable 객체를 전달해주면 페이징 처리를 해줌

        //  페이징 처리 O, 페이지 번호가 필요하다 => Page 반환
        //  페이징 처리 O, 페이지 번호가 필요없다 => Slice 반환
        Page<Board> result = boardRepository.findAll(pageRequest);  // 반환을 받을 때 Page로 받고 있음 이걸 Slice로 반환 받으면 무한 스크롤로 변함

        return BoardDto.PageRes.from(result);
    }

    public BoardDto.ReadRes read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        return BoardDto.ReadRes.from(board);
    }

    @Transactional
    public BoardDto.RegRes update(Long idx, BoardDto.RegReq dto) {
        Board board = boardRepository.findById(idx).orElseThrow();
        board.update(dto);

        boardRepository.save(board);

        return BoardDto.RegRes.from(board);
    }

    public void delete(Long idx) {
        boardRepository.deleteById(idx);
    }
}
