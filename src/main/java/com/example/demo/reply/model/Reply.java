package com.example.demo.reply.model;


import com.example.demo.board.model.Board;
import com.example.demo.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_idx")
    private Board board;

}
