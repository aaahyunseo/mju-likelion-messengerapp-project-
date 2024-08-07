package org.mjulikelion.messengerapplication.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity(name = "member")
public class Member extends BaseEntity{
    @Setter
    @Column(length = 50, nullable = false)
    private String name;        //이름
    @Setter
    @Column(length = 100, nullable = false)
    private String email;       //이메일
    @Setter
    @Column(length = 50, nullable = false)
    private String password;    //비밀번호

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberMessage> sendMemberMessages; //발신 메시지 목록
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberMessage> receiveMemberMessages; //수신 메시지 목록
}
