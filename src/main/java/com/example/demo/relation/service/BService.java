package com.example.demo.relation.service;

import com.example.demo.relation.model.B;
import com.example.demo.relation.repository.BRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BService {
    private final BRepository bRepository;

    public void read(Long idx) {
        B b = bRepository.findById(idx).orElseThrow();

        System.out.println(b.getIdx());
        System.out.println(b.getB01());
        System.out.println(b.getB02());

        System.out.println(b.getA().getIdx());
        System.out.println(b.getA().getA01());

        System.out.println("");
    }

    public void list() {
        List<B> bList = bRepository.findAll();
        for (B b: bList) {
            System.out.println(b.getIdx());
            System.out.println(b.getB01());
            System.out.println(b.getB02());

            System.out.println(b.getA().getIdx());
            System.out.println(b.getA().getA01());

        }

        System.out.println("");
    }
}
