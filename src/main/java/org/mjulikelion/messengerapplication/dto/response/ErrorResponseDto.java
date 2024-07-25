package org.mjulikelion.messengerapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mjulikelion.messengerapplication.exception.CustomException;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private final String errorCode;     //예외 코드
    private final String message;       //예외 메세지
    private final String detail;        //예외 상세 정보

    public static ErrorResponseDto res(final CustomException customException) {
        String errorCode = customException.getErrorCode().getCode();
        String message = customException.getErrorCode().getMessage();
        String detail = customException.getDetail();
        return new ErrorResponseDto(errorCode, message, detail);
    }

    public static ErrorResponseDto res(final String errorCode, final Exception exception) {
        return new ErrorResponseDto(errorCode, exception.getMessage(), null);
    }
}