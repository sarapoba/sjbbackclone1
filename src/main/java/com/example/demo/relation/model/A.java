package com.example.demo.relation.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class A {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String a01;

    // 외래키 속성 지정
    // 관계의 주인이 누구인지 지정
    // FetchType 기본값이 LAZY : 연관 관계의 엔티티의 값을 값이 사용될 때 SQL을 한 번 더 실행해서 가져온다.
    //                  EAGER : 현재 엔티티를 조회하면서 연관 관계의 엔티티의 값도 같이 가져온다.

    @OneToMany(
            mappedBy = "a",
            fetch = FetchType.LAZY
            /*, fetch = FetchType.EAGER*/
    )
    private List<B> bList;
}
