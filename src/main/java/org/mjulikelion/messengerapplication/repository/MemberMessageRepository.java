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
    List<MemberMessage> findAllByRecipient(Member recipient);
    MemberMessage findAllBySender(String sender);
    boolean existsBySenderAndMessage(String sender, Message message); //메시지와 발신자가 일치하는지 확인하기
    boolean existsByRecipientAndMessage(Member recipient, Message message); //메시지와 수신자가 일치하는지 확인하기
}
