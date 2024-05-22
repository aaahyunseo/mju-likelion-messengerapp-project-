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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MemberMessageRepository memberMessageRepository;

    //메시지 목록 전체 조회 - 발신자, 메시지ID, 전송시간, 읽음여부
    public MessageListData getMessageList(Member recipient){
        List<MemberMessage> messages = memberMessageRepository.findAllByRecipient(recipient.getId());
        List<MessageAllResponseDto> list = new ArrayList<>();
        for(MemberMessage memberMessage : messages){
            MessageAllResponseDto messageAllResponseDto = MessageAllResponseDto.builder()
                    .sender(memberMessage.getSender().getName())
                    .messageId(memberMessage.getMessage().getId())
                    .time(memberMessage.getMessage().getCreatedAt())
                    .readMessage(memberMessage.getMessage().isReadMessage())
                    .build();
            list.add(messageAllResponseDto);
        }
        MessageListData messageListData = MessageListData.builder().messageList(list).build();
        return messageListData;
    }

    //특정 메시지 조회하기 - 발신자, 문자내용, 전송시간
    public MessageResponseData getMessageById(Member recipient, UUID id){
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

    //메시지 작성 - 여러 명의 수신자가 있을 수 있음. 리스트로 받음.
    public void writeMessage(Member sender, WriteMessageDto writeMessageDto){
        List<String> recipients = writeMessageDto.getRecipients();
        for(String name: recipients){
            existsRecipient(memberRepository.findMemberByName(name));
            Member recipient = memberRepository.findMemberByName(name);

            Message newMessage = Message.builder()
                    .content(writeMessageDto.getContent())
                    .sender(sender)
                    .recipient(recipient.getId())
                    .readMessage(false)
                    .build();
            messageRepository.save(newMessage);

            MemberMessage memberMessage = MemberMessage.builder()
                    .message(newMessage)
                    .sender(sender)
                    .recipient(recipient.getId())
                    .chat(newMessage.getId())
                    .build();
            memberMessageRepository.save(memberMessage);
        }
    }

    //메시지 수정
    public void updateMessageById(Member sender, UpdateMessageDto updateMessageDto, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        checkSender(sender, id);    //발신자와 메시지가 일치하는지
        Message message = messageRepository.findMessageById(id);
        if(!message.isReadMessage()){
            //메시지를 읽지 않았을 경우
            Message updateMessage = messageRepository.findMessageById(id);
            updateMessage.setContent(updateMessageDto.getContent());
            messageRepository.save(updateMessage);
            return;
        } throw new ForbiddenException(ErrorCode.NO_ACCESS,"수신자가 메시지를 읽어 수정할 수 없습니다.");
    }

    //메시지 삭제
    public void deleteMessageById(Member sender, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        checkSender(sender, id);    //발신자와 메시지가 일치하는지
        Message message = messageRepository.findMessageById(id);
        if(!message.isReadMessage()) {
            //메시지를 읽지 않았을 경우
            messageRepository.deleteById(id);
            return;
        } throw new ForbiddenException(ErrorCode.NO_ACCESS,"수신자가 메시지를 읽어 삭제할 수 없습니다.");
    }

    //답장 작성
    public void writeComment(Member sender, CommentDto commentDto, UUID id){
        existsMessage(id);  //ID에 해당하는 메시지가 존재하는지
        checkRecipient(sender,id);  //답장 작성자가 해당 메시지 수신자인지
        Message chat = messageRepository.findMessageById(id);   //답장을 보낼 메시지 원본
        Message comment = Message.builder()
                .content(commentDto.getContent())
                .sender(sender)
                .recipient(chat.getSender().getId())    //메시지 원본의 발신자가 답장의 수신자가 됨
                .readMessage(false)
                .build();
        messageRepository.save(comment);

        MemberMessage memberMessage = MemberMessage.builder()
                .message(comment)
                .sender(sender)
                .recipient(chat.getSender().getId())
                .chat(chat.getId())
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
    public boolean checkSender(Member sender, UUID id){
        Message message = messageRepository.findMessageById(id);
        if(memberMessageRepository.existsBySenderAndMessage(sender, message)){
            return true;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지 접근 권한이 없습니다.");
    }

    //메시지 수신자가 맞는지 확인하기
    public boolean checkRecipient(Member recipient, UUID id){
        Message message = messageRepository.findMessageById(id);
        if(memberMessageRepository.existsByRecipientAndMessage(recipient.getId(), message)){
            return true;
        }
        throw new ForbiddenException(ErrorCode.NO_ACCESS,"메시지 접근 권한이 없습니다.");
    }
}
