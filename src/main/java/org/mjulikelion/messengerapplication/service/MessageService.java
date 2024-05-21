package org.mjulikelion.messengerapplication.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.dto.request.CommentDto;
import org.mjulikelion.messengerapplication.dto.request.UpdateMessageDto;
import org.mjulikelion.messengerapplication.dto.request.WriteMessageDto;
import org.mjulikelion.messengerapplication.dto.response.*;
import org.mjulikelion.messengerapplication.exception.ForbiddenException;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.model.Comment;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.model.MemberMessage;
import org.mjulikelion.messengerapplication.model.Message;
import org.mjulikelion.messengerapplication.repository.CommentRepository;
import org.mjulikelion.messengerapplication.repository.MemberMessageRepository;
import org.mjulikelion.messengerapplication.repository.MemberRepository;
import org.mjulikelion.messengerapplication.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MemberMessageRepository memberMessageRepository;
    private final CommentRepository commentRepository;

    //메시지 목록 전체 조회
    public MessageListData getMessageList(Member recipient){
        List<MemberMessage> messages = memberMessageRepository.findAllByRecipient(recipient);
        List<MessageAllResponseDto> list = new ArrayList<>();
        for(MemberMessage memberMessage : messages){
            MessageAllResponseDto messageAllResponseDto = MessageAllResponseDto.builder()
                    .sender(memberMessage.getSender())
                    .messageId(memberMessage.getMessage().getId())
                    .time(memberMessage.getMessage().getCreatedAt())
                    .readMessage(memberMessage.getMessage().isReadMessage())
                    .build();
            list.add(messageAllResponseDto);
        }
        MessageListData messageListData = MessageListData.builder().messageList(list).build();
        return messageListData;
    }

    //특정 메시지 조회하기 - 발신자와 문자 내용 조회 가능
    public MessageResponseData getMessage(Member recipient, UUID id){
        existsMessage(id);
        checkRecipient(recipient, id);
        Message message = messageRepository.findMessageById(id);
        MessageResponseData messageResponseData = MessageResponseData.builder()
                .sender(message.getSender().getName())
                .content(message.getContent())
                .time(message.getCreatedAt())
                .build();
        message.setReadMessage(true);
        messageRepository.save(message);
        return messageResponseData;
    }

    //메시지 작성
    public void writeMessage(Member sender, WriteMessageDto writeMessageDto){
        for(String recipientName : writeMessageDto.getRecipients()){
            Member recipient = memberRepository.findMemberByName(recipientName);
            existsRecipient(recipient);
            Message newMessage = Message.builder()
                    .content(writeMessageDto.getContent())
                    .sender(sender)
                    .readMessage(false)
                    .build();
            messageRepository.save(newMessage);

            MemberMessage memberMessage = MemberMessage.builder()
                    .message(newMessage)
                    .recipient(recipient)
                    .sender(sender.getName())
                    .build();
            memberMessageRepository.save(memberMessage);
        }
    }

    //메시지 수정
    public void updateMessage(Member sender, UpdateMessageDto updateMessageDto, UUID id){
        existsMessage(id);
        checkSender(sender, id);
        Message message = messageRepository.findMessageById(id);
        if(!message.isReadMessage()){
            //메시지를 읽지 않았을 경우
            Message updateMessage = messageRepository.findMessageById(id);
            updateMessage.setContent(updateMessageDto.getContent());
            messageRepository.save(updateMessage);
            return;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지를 수정할 수 없습니다.");
    }

    //메시지 삭제
    public void deleteMessage(Member sender, UUID id){
        existsMessage(id);
        checkSender(sender, id);
        Message message = messageRepository.findMessageById(id);
        if(!message.isReadMessage()) {
            //메시지를 읽지 않았을 경우
            messageRepository.deleteById(id);
            return;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지를 삭제할 수 없습니다.");
    }

    //답장 작성
    public void writeComment(Member sender, CommentDto commentDto, UUID id){
        existsMessage(id);
        checkRecipient(sender,id);  //답장 작성자가 해당 메시지 수신자인지 확인
        MemberMessage memberMessage = memberMessageRepository.findAllBySender(sender.getName());
        Comment comment = Comment.builder()
                .message(memberMessage.getMessage())
                .content(commentDto.getContent())
                .sender(sender)
                .build();
        commentRepository.save(comment);
    }

    //답장 조회
    public CommentListData getCommentList(Member recipient, UUID id){
        existsMessage(id);
        checkRecipient(recipient, id);
        Message message = messageRepository.findMessageById(id);
        List<Comment> comments = commentRepository.findAllCommentByMessage(message);
        List<CommentResponseDto> list = new ArrayList<>();
        for(Comment comment : comments){
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                    .content(comment.getContent())
                    .sender(comment.getSender().getName())
                    .build();
            list.add(commentResponseDto);
        }
        CommentListData commentListData = CommentListData.builder().commentList(list).build();
        return commentListData;
    }

    //수신자 존재여부 확인하기
    public boolean existsRecipient(Member recipient){
        if(memberRepository.existsById(recipient.getId())){
            return true;
        }
        throw new NotFoundException(ErrorCode.NOT_FOUND_MEMBER);
    }

    //메시지 존재여부 확인하기
    public boolean existsMessage(UUID id){
        if(messageRepository.existsById(id)){
            return true;
        }
        throw new NotFoundException(ErrorCode.NOT_FOUND_MESSAGE);
    }

    //메시지 발신자가 맞는지 확인하기
    public boolean checkSender(Member sender, UUID id){
        Message message = messageRepository.findMessageById(id);
        if(memberMessageRepository.existsBySenderAndMessage(sender.getName(), message)){
            return true;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지 접근 권한이 없습니다.");
    }

    //메시지 수신자가 맞는지 확인하기
    public boolean checkRecipient(Member recipient, UUID id){
        Message message = messageRepository.findMessageById(id);
        if(memberMessageRepository.existsByRecipientAndMessage(recipient, message)){
            return true;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지 접근 권한이 없습니다.");
    }
}
