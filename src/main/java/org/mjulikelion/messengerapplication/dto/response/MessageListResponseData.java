package org.mjulikelion.messengerapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MessageListResponseData {
    private List<MessageDto> messageList;
}
