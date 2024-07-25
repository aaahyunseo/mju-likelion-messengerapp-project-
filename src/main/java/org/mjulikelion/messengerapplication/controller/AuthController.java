package org.mjulikelion.messengerapplication.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.authentication.JwtTokenProvider;
import org.mjulikelion.messengerapplication.dto.request.JoinDto;
import org.mjulikelion.messengerapplication.dto.request.LoginDto;
import org.mjulikelion.messengerapplication.dto.response.ResponseDto;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/join")
    private ResponseEntity<ResponseDto<Void>> join(@RequestBody @Valid JoinDto joinDto){
        memberService.join(joinDto);
        return new ResponseEntity<> (ResponseDto.res(HttpStatus.CREATED,"회원가입에 성공했습니다."), HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    private ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response){
        Member member = memberService.login(loginDto);
        //member ID를 payload로 사용
        String payload = member.getId().toString();
        //AccessToken 생성
        String accessToken = jwtTokenProvider.createToken(payload);
        //쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("AccessToken", "Bearer=" + accessToken)
                .maxAge(Duration.ofMillis(1800000))// 30분
                .path("/")// 모든 경로에서 접근 가능
                .build();
        response.addHeader("set-cookie", cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그인 되었습니다."), HttpStatus.OK);
    }

    //로그아웃
    @DeleteMapping("/logout")
    private  ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse response){
        //쿠키 삭제
        Cookie cookie = new Cookie("AccessToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"로그아웃 되었습니다."),HttpStatus.OK);
    }
}
