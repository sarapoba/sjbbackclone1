package com.example.demo.relation.service;


import com.example.demo.relation.model.A;
import com.example.demo.relation.model.ADto;
import com.example.demo.relation.repository.ARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AService {
    private final ARepository aRepository;

    public ADto.ARes read(Long idx){
        A a = aRepository.findById(idx).orElseThrow();

        return ADto.ARes.from(a);
    }

    public void list(){
        List<A> aList = aRepository.findAll();

        for(A a: aList){
            System.out.println(a.getIdx());
            System.out.println(a.getA01());

            System.out.println(a.getBList().get(0).getIdx());
            System.out.println(a.getBList().get(0).getB01());
            System.out.println(a.getBList().get(0).getB02());
        }

        System.out.println("");
    }
}
