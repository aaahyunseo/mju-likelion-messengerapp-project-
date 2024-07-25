package org.mjulikelion.messengerapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.annotation.AuthenticatedMember;
import org.mjulikelion.messengerapplication.dto.request.DeleteMemberDto;
import org.mjulikelion.messengerapplication.dto.request.UpdateMemberDto;
import org.mjulikelion.messengerapplication.dto.response.ResponseDto;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    //회원탈퇴
    @DeleteMapping
    private ResponseEntity<ResponseDto<Void>> deleteMember(@AuthenticatedMember Member member
            , @RequestBody @Valid DeleteMemberDto deleteMemberDto){
        memberService.deleteMember(member,deleteMemberDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "회원탈퇴 성공하였습니다."), HttpStatus.OK);
    }

    //회원수정
    @PatchMapping
    private ResponseEntity<ResponseDto<Void>> updateMember(@AuthenticatedMember Member member
            ,@RequestBody @Valid UpdateMemberDto updateMemberDto){
        memberService.updateMember(member, updateMemberDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "회원정보 수정이 완료되었습니다."), HttpStatus.OK);
    }
}
