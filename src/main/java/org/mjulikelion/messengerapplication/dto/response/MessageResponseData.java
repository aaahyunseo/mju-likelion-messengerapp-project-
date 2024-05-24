package org.mjulikelion.messengerapplication.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.messengerapplication.model.Member;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageResponseData {
    private String sender;  //발신자
    private String content; //메시지 내용
    private LocalDateTime time; //보낸 시간
}
