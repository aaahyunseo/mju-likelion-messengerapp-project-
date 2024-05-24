package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity(name = "message")
public class Message extends BaseEntity{
    @Setter
    @Column(length = 500, nullable = false)
    private String content;     //메세지 내용
    @Setter
    @Column(nullable = false)
    private boolean readMessage;   //메시지 열람여부
    @Setter
    @Column(nullable = false)
    private String originMessage;          //어떤 메시지에 대한 답장인지

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberMessage> memberMessages;     //메시지 전체 목록
}
