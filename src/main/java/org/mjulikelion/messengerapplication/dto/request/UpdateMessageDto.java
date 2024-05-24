package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateMessageDto {
    @NotBlank(message = "수정할 내용이 누락되었습니다.")
    private String content;     //수정할 메시지 내용
}
