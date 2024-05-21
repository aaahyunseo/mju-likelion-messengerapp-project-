package org.mjulikelion.messengerapplication.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedMember;
import org.mjulikelion.messengerapplication.dto.response.ResponseDto;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    //회원탈퇴
    @DeleteMapping
    private ResponseEntity<ResponseDto<Void>> deleteMember(@AuthenticatedMember Member member){
        memberService.deleteMember(member);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "회원탈퇴 성공하였습니다."), HttpStatus.OK);
    }
}
