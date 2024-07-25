package org.mjulikelion.messengerapplication.exception;

import lombok.Getter;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;  //에러코드
    private final String detail;        //에러 발생 원인

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = null;
    }

    public CustomException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }

}
