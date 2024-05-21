package org.mjulikelion.messengerapplication.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.messengerapplication.model.Member;

@Getter
@Builder
public class CommentResponseDto {
    private String content;
    private String sender;
}
