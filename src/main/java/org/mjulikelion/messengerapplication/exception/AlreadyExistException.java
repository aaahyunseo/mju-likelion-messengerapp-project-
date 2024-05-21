package org.mjulikelion.messengerapplication.exception;

import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

public class AlreadyExistException extends CustomException{
    public AlreadyExistException(ErrorCode errorCode, String detail){super(errorCode,detail);}
}
