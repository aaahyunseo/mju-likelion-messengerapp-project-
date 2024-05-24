package org.mjulikelion.messengerapplication.authentication;

import lombok.Getter;
import lombok.Setter;
import org.mjulikelion.messengerapplication.model.Member;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class AuthenticationContext {
    //요청 스코프 내에서 인증된 멤버를 담을 컨텍스트
    private Member principal;
}
