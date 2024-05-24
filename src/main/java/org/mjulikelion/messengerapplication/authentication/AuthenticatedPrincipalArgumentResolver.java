package org.mjulikelion.messengerapplication.authentication;

import lombok.RequiredArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedMember;
import org.mjulikelion.messengerapplication.model.Member;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthenticationContext authenticationContext;

    //어떤 어노테이션의 기능인지
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class);
    }

    //어노테이션의 실제 동작 정의
    @Override
    public Member resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory){
        //컨텍스트 내에 저장된 인증된 멤버 반환
        return authenticationContext.getPrincipal();
    }
}
