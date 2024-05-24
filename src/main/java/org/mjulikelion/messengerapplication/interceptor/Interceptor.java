package org.mjulikelion.messengerapplication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.messengerapplication.authentication.AuthenticationContext;
import org.mjulikelion.messengerapplication.authentication.AuthenticationExtractor;
import org.mjulikelion.messengerapplication.authentication.JwtTokenProvider;
import org.mjulikelion.messengerapplication.exception.NotFoundException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final AuthenticationContext authenticationContext;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        //HttpServletReqeust에서 accessToken 추출
        String accessToken = AuthenticationExtractor.extract(request);
        //Jwt Token에서 payload 안에 memberId 추출
        UUID memberId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        //추출된 memberId 통해 member 검증, 일치할 경우 해당 member 추출
        Member member = findExistMember(memberId);
        //검증된 member 객체를 담는 AuthenticationContext에 member 저장
        authenticationContext.setPrincipal(member);
        return true;
    }

    private Member findExistMember(final UUID memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));
    }
}
