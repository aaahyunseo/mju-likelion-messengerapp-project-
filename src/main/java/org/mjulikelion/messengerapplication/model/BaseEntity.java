package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass   //상속을 받는 Entity 클래스에게 매핑 정보만 제공
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    //PK 생성
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    //@CreatedDate, @LastModifiedDate 은 Entity가 생성될 때나 수정될 때 자동으로 값을 넣어주는 역할
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
