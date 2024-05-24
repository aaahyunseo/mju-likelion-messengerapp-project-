package org.mjulikelion.messengerapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MessageDto {
    private UUID messageId;    //메시지 id
    private String sender;  //수신자 이름
    private LocalDateTime time; //보낸 시간
    private boolean readMessage;   //메시지 열람여부
}
