package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity(name = "message")
public class Message extends BaseEntity{
    @Setter
    @Column(length = 300, nullable = false)
    private String content;     //메세지 내용
    @Setter
    @Column(nullable = false)
    private boolean readMessage;   //메시지 열람여부

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member sender;      //발신자

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comment;    //해당 메세지에 작성된 답장
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberMessage> memberMessage;
}
