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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;      //해당 메시지를 보낸 멤버(발신자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Member recipient;      //해당 메시지를 받은 멤버(수신자)
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberMessage> memberMessages;     //메시지 전체 목록
}
