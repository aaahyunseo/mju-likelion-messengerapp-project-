package org.mjulikelion.messengerapplication.repository;

import org.mjulikelion.messengerapplication.model.Comment;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllCommentByMessage(Message message);
}
