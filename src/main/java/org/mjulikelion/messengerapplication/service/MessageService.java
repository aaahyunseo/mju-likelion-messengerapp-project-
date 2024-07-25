package org.mjulikelion.messengerapplication.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.messengerapplication.dto.request.CommentDto;
import org.mjulikelion.messengerapplication.dto.request.UpdateMessageDto;
import org.mjulikelion.messengerapplication.dto.request.WriteMessageDto;
import org.mjulikelion.messengerapplication.dto.response.*;
import org.mjulikelion.messengerapplication.exception.ForbiddenException;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.model.MemberMessage;
import org.mjulikelion.messengerapplication.model.Message;
import org.mjulikelion.messengerapplication.repository.MemberMessageRepository;
import org.mjulikelion.messengerapplication.repository.MemberRepository;
import org.mjulikelion.messengerapplication.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MemberMessageRepository memberMessageRepository;

    //메시지 목록 전체 조회 - 발신자, 메시지ID, 전송시간, 읽음여부
    public MessageListResponseData getMessageList(Member recipient){
        List<MessageDto> memos = memberMessageRepository.findAllByRecipient(recipient).stream()
                //중간 연산 - MessageDto 형식으로 변환
                .map(memberMessage -> MessageDto.builder()
                        .sender(memberMessage.getSender().getName())
                        .messageId(memberMessage.getMessage().getId())
                        .time(memberMessage.getCreatedAt())
                        .readMessage(memberMessage.getMessage().isReadMessage())
                        .build())
                //최종 연산 - 스트림 요소를 수집하여 컬렉션에 담아서 반환
                .collect(Collectors.toList());
        MessageListResponseData messageListData = MessageListResponseData.builder().messageList(memos).build();
        return messageListData;
    }

    //특정 메시지 조회하기 - 발신자, 문자내용, 전송시간
    public MessageResponseData getMessageById(Member recipient, UUID id){
        existsMessage(id);
        Message message = messageRepository.findMessageById(id);
        checkRecipient(recipient, message);
        MemberMessage memberMessage = memberMessageRepository.findMemberMessageByMessage(message);
        MessageResponseData messageResponseData = MessageResponseData.builder()
                .sender(memberMessage.getSender().getName())
                .content(message.getContent())
                .time(message.getCreatedAt())
                .build();
        message.setReadMessage(true);
        messageRepository.save(message);
        return messageResponseData;
    }

    //메시지 작성 - 여러 명의 수신자가 있을 수 있음. 리스트로 받음.
    public void writeMessage(Member sender, WriteMessageDto writeMessageDto){
        List<String> recipients = writeMessageDto.getRecipients();
        recipients.stream()
                //중간연산 - 수신자 이름 존재 여부 확인 후 이름에 맞는 멤버 반환
            .map(name -> {
                existsRecipient(memberRepository.findMemberByName(name));
                return memberRepository.findMemberByName(name);
            })
                //최종연산 - 각 요소에 지정된 작업 수행
            .forEach(recipient -> {
                Message newMessage = Message.builder()
                        .content(writeMessageDto.getContent())
                        .readMessage(false)
                        .originMessage("First Message")
                        .build();
                messageRepository.save(newMessage);

                MemberMessage memberMessage = MemberMessage.builder()
                        .message(newMessage)
                        .sender(sender)
                        .recipient(recipient)
                        .build();
                memberMessageRepository.save(memberMessage);
            });
    }

    //메시지 수정
    public void updateMessageById(Member sender, UpdateMessageDto updateMessageDto, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        Message message = messageRepository.findMessageById(id);
        checkSender(sender, message);    //발신자와 메시지가 일치하는지
        if(!message.isReadMessage()){
            //메시지를 읽지 않았을 경우
            Message updateMessage = messageRepository.findMessageById(id);
            updateMessage.setContent(updateMessageDto.getContent());
            messageRepository.save(updateMessage);
            return;
        } throw new ForbiddenException(ErrorCode.NO_ACCESS,"수신자가 메시지를 읽어 수정할 수 없습니다.");
    }

    //메시지 삭제 - 발신자가 삭제
    public void deleteMessageBySender(Member sender, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        Message message = messageRepository.findMessageById(id);
        checkSender(sender, message);    //발신자와 메시지가 일치하는지
        if(!message.isReadMessage()) {
            //메시지를 읽지 않았을 경우
            messageRepository.deleteById(id);
            return;
        } throw new ForbiddenException(ErrorCode.NO_ACCESS,"수신자가 메시지를 읽어 삭제할 수 없습니다.");
    }

    //메시지 삭제 - 수신자가 삭제
    public void deleteMessageByRecipient(Member recipient, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        Message message = messageRepository.findMessageById(id);
        checkRecipient(recipient, message);  //수신자와 메시지가 일치하는지
        messageRepository.deleteById(id);
    }

    //답장 작성
    public void writeComment(Member sender, CommentDto commentDto, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        Message originalMessage = messageRepository.findMessageById(id);   //답장을 보낼 메시지 원본
        checkRecipient(sender, originalMessage);  //답장 작성자가 해당 메시지 수신자인지
        MemberMessage originalMemberMessage = memberMessageRepository.findMemberMessageByMessage(originalMessage);
        Message comment = Message.builder()
                .content(commentDto.getContent())
                .originMessage(originalMessage.getId().toString())
                .readMessage(false)
                .build();
        messageRepository.save(comment);

        MemberMessage memberMessage = MemberMessage.builder()
                .message(comment)
                .sender(sender)
                .recipient(originalMemberMessage.getSender())   //메시지 원본의 발신자가 답장의 수신자가 됨
                .build();
        memberMessageRepository.save(memberMessage);
    }

    //수신자 존재여부 확인하기
    public boolean existsRecipient(Member recipient){
        if(recipient!=null && memberRepository.existsById(recipient.getId())){
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
    public boolean checkSender(Member sender, Message message){
        if(memberMessageRepository.existsBySenderAndMessage(sender, message)){
            return true;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지 접근 권한이 없습니다.");
    }

    //메시지 수신자가 맞는지 확인하기
    public boolean checkRecipient(Member recipient, Message message){
        if(memberMessageRepository.existsByRecipientAndMessage(recipient, message)){
            return true;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지 접근 권한이 없습니다.");
    }
}
