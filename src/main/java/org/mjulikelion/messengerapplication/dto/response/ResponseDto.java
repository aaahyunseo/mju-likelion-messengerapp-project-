package org.mjulikelion.messengerapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final String message;   //요청 결과 메세지
    private final String httpStatusCode;    //http 상태 코드
    private final T data;   //요청 결과 반환 데이터

    //반환 데이터가 없는 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode httpStatusCode, final String message) {
        return new ResponseDto<>(String.valueOf(httpStatusCode.value()), message, null);
    }

    //반환 데이터가 있는 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode httpStatusCode, final String message, final T data) {
        return new ResponseDto<>(String.valueOf(httpStatusCode.value()), message, data);
    }
}
