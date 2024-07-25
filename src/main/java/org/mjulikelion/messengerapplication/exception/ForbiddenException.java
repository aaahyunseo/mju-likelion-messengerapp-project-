package org.mjulikelion.messengerapplication.exception;

import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

public class ForbiddenException extends CustomException{
    public ForbiddenException(ErrorCode errorCode, String detail){super(errorCode, detail);}
}
