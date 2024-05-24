package org.mjulikelion.messengerapplication.repository;

import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.model.MemberMessage;
import org.mjulikelion.messengerapplication.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberMessageRepository extends JpaRepository<MemberMessage, UUID> {
    List<MemberMessage> findAllByRecipient(Member recipient); //수신자의 메시지 목록 반환
    boolean existsBySenderAndMessage(Member sender, Message message);    //발신자와 메시지 일치 여부
    boolean existsByRecipientAndMessage(Member sender, Message message);  //수신자와 메시지 일치 여부
    MemberMessage findMemberMessageByMessage(Message message);
}
