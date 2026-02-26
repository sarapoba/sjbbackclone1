package com.example.demo.relation.repository;

import com.example.demo.relation.model.A;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ARepository extends JpaRepository<A, Long> {
}
