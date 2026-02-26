package com.example.demo.reply.model;

import lombok.Builder;
import lombok.Getter;

public class ReplyDto {
    @Builder
    @Getter
    public static class ReplyRes{
        private Long idx;
        private String contents;
        private String writer;

        public static ReplyDto.ReplyRes from(Reply entity){
            return ReplyRes.builder()
                    .idx(entity.getIdx())
                    .contents(entity.getContents())
                    .writer(entity.getUser().getName())
                    .build();
        }
    }
}
