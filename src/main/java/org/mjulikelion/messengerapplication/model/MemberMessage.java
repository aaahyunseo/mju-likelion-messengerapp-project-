package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Builder
@Entity(name = "member_message")
public class MemberMessage extends BaseEntity{

    @Setter
    @Column(nullable = false)
    private String sender;     //발신자

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Member recipient;  //수신자

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;
}
