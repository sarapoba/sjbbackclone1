package com.example.demo.likes;

import com.example.demo.board.model.Board;
import com.example.demo.likes.model.Likes;
import com.example.demo.user.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    public void like(AuthUserDetails user, Long boardIdx) {
        Likes likes = Likes.builder()
                .user(user.toEntity())
                .board(Board.builder().idx(boardIdx).build())
                .build();

        likesRepository.save(likes);
    }
}
