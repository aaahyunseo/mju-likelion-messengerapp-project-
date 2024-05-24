package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentDto {
    @NotBlank(message = "답장 내용이 누락되었습니다.")
    private String content;
}
