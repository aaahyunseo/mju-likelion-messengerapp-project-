package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Getter
@Entity(name = "comment")
public class Comment extends BaseEntity{

    @Setter
    @Column(length = 300, nullable = false)
    private String content; //내용

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;    //해당 답장이 작성된 메세지
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;      //해당 답장을 보낸 멤버(발신자)
}
