package org.mjulikelion.messengerapplication.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;

public class AuthenticationExtractor {
    private static final String TOKEN_COOKIE_NAME = "AccessToken";

    //JWT Token 추출
    public static String extract(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    //"Bearer " 를 제외한 뒷 부분 jwt token 값만 추출
                    return cookie.getValue().replace("=", " ").substring(7);
                }
            }
        }
        throw new NotFoundException(ErrorCode.NOT_FOUND_TOKEN);
    }
}