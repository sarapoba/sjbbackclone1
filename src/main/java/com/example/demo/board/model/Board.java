package com.example.demo.board.model;

import com.example.demo.common.model.BaseEntity;
import com.example.demo.reply.model.Reply;
import com.example.demo.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "board")
    List<Reply> replyList;

    public void update(BoardDto.RegReq dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
