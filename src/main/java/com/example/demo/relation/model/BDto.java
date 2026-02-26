package com.example.demo.relation.model;

import lombok.Builder;
import lombok.Getter;

public class BDto {
    @Builder
    @Getter
    public static class BRes {
        private Long idx;
        private String b01;
        private String b02;

        public static BDto.BRes from(B entity) {
            return BDto.BRes.builder()
                    .idx(entity.getIdx())
                    .b01(entity.getB01())
                    .b02(entity.getB02())
                    .build();
        }
    }
}
