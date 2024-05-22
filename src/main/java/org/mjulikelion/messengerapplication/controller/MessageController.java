package org.mjulikelion.messengerapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedMember;
import org.mjulikelion.messengerapplication.dto.request.CommentDto;
import org.mjulikelion.messengerapplication.dto.request.UpdateMessageDto;
import org.mjulikelion.messengerapplication.dto.request.WriteMessageDto;
import org.mjulikelion.messengerapplication.dto.response.MessageListData;
import org.mjulikelion.messengerapplication.dto.response.MessageResponseData;
import org.mjulikelion.messengerapplication.dto.response.ResponseDto;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    //메시지 목록 전체 조회
    @GetMapping
    private ResponseEntity<ResponseDto<MessageListData>> getMessageList(@AuthenticatedMember Member recipient){
        MessageListData messageListData = messageService.getMessageList(recipient);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메시지 목록을 조회합니다.", messageListData),HttpStatus.OK);
    }

    //메시지 조회
    @GetMapping("/{id}")
    private ResponseEntity<ResponseDto<MessageResponseData>> getMessageById(@AuthenticatedMember Member recipient, @PathVariable("id") UUID id){
        MessageResponseData messageResponseData = messageService.getMessageById(recipient, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메시지를 조회합니다.", messageResponseData),HttpStatus.OK);
    }

    //메시지 작성
    @PostMapping
    private ResponseEntity<ResponseDto<Void>> writeMessage(@AuthenticatedMember Member sender, @RequestBody @Valid WriteMessageDto writeMessageDto){
        messageService.writeMessage(sender, writeMessageDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED,"메세지가 작성되었습니다."),HttpStatus.CREATED);
    }

    //메시지 수정
    @PatchMapping("/{id}")
    private ResponseEntity<ResponseDto<Void>> updateMessageById(@AuthenticatedMember Member sender
            ,@RequestBody @Valid UpdateMessageDto updateMessageDto, @PathVariable("id") UUID id){
        messageService.updateMessageById(sender, updateMessageDto, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메시지가 수정되었습니다."), HttpStatus.OK);
    }

    //메시지 삭제
    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseDto<Void>> deleteMessageById(@AuthenticatedMember Member sender, @PathVariable("id") UUID id){
        messageService.deleteMessageById(sender, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"메시지가 삭제되었습니다."), HttpStatus.OK);
    }

    //답장쓰기
    @PostMapping("/{id}/comment")
    private ResponseEntity<ResponseDto<Void>> writeComment(@AuthenticatedMember Member sender
            , @RequestBody @Valid CommentDto commentDto, @PathVariable("id") UUID id){
        messageService.writeComment(sender, commentDto, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED,"답장 완료하였습니다."), HttpStatus.CREATED);
    }
}
