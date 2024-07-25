package org.mjulikelion.messengerapplication.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //NotFoundException
    NOT_FOUND_MEMBER("4040","멤버를 찾을 수 없습니다."),
    NOT_FOUND_MESSAGE("4041","메시지를 찾을 수 없습니다."),
    NOT_FOUND_TOKEN("4042","토큰을 찾을 수 없습니다."),

    //ForbiddenException
    LOGIN_FALSE("4031", "로그인에 실패하였습니다."),
    TOKEN_INVALID("4032", "유효하지 않는 토큰입니다."),
    NO_ACCESS("4033", "접근 권한이 없습니다."),

    //AlreadyExistException
    ALREADY_EXIST("4090","이미 존재합니다."),

    NOT_NULL("9001", "필수값이 누락되었습니다."),
    NOT_BLANK("9002", "필수값이 빈 값이거나 공백으로 되어있습니다.");

    private final String code;
    private final String message;

    //Dto의 어노테이션을 통해 발생한 에러코드를 반환
    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
