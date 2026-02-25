package com.example.demo.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.sql.Timestamp;
import java.time.Instant;



@MappedSuperclass   //  상속받아간 변수도 DB 테이블에 적용
//  엔티티의 변화를 감시하는 리스너
//  @PrePersist, @PreUpdate와 같은 특정 시점을 감지하기 위해 사용
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @Column(name = "create_date", updatable = false, nullable = false)
    private Date createdAt;

    @Column(name = "update_date", nullable = false)
    private Date updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
