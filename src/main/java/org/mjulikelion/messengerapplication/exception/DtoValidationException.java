package org.mjulikelion.messengerapplication.exception;

import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

public class DtoValidationException extends CustomException{
    public DtoValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
