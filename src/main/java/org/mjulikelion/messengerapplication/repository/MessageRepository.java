package org.mjulikelion.messengerapplication.repository;

import org.mjulikelion.messengerapplication.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    Message findMessageById(UUID id);   //ID에 해당하는 메시지 반환
}
