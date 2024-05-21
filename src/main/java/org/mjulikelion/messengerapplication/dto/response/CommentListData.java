package org.mjulikelion.messengerapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentListData {
    private List<CommentResponseDto> commentList;
}
