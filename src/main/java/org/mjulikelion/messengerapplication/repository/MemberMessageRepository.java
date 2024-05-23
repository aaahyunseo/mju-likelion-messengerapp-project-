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
    List<MemberMessage> findAllByRecipient(Member recipient);         //수신자의 메시지 목록 전제 반환
    boolean existsBySenderAndMessage(Member sender, Message message); //메시지와 발신자가 일치하는지 확인하기
    boolean existsByRecipientAndMessage(Member recipient, Message message); //메시지와 수신자가 일치하는지 확인하기
}
