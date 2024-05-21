package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class WriteMessageDto {
    @NotBlank(message = "메세지 내용이 누락되었습니다.")
    @Size(min = 1, max = 100)
    private String content; //메시지 내용
    @NotEmpty(message = "수신자 목록이 누락되었습니다.")
    private List<String> recipients;  //수신자
}
