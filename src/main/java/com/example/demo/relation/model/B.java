package com.example.demo.relation.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class B {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String b01;
    private String b02;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="a_idx")
    private A a;
}
