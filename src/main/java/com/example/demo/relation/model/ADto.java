package com.example.demo.relation.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ADto {
    @Builder
    @Getter
    public static class ARes {
        private Long idx;
        private String a01;
        private List<BDto.BRes> bList;

        public static ARes from(A entity) {
            return ARes.builder()
                    .idx(entity.getIdx())
                    .a01(entity.getA01())
                    .bList(entity.getBList().stream().map(BDto.BRes::from).toList())
                    .build();
        }
    }
}
