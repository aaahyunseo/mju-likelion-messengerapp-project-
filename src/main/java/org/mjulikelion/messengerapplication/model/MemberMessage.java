package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Entity(name = "member_message")
public class MemberMessage extends BaseEntity{
    @Setter
    @Column(nullable = false)
    private UUID chat;          //어떤 메시지에 대한 답장인지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;     // 발신자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Member recipient;     // 수신자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;    //메시지 정보
}
