package org.mjulikelion.messengerapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MessageListData {
    private List<MessageAllResponseDto> messageList;
}
