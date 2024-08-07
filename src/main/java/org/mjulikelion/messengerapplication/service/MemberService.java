package org.mjulikelion.messengerapplication.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.messengerapplication.dto.request.DeleteMemberDto;
import org.mjulikelion.messengerapplication.dto.request.JoinDto;
import org.mjulikelion.messengerapplication.dto.request.LoginDto;
import org.mjulikelion.messengerapplication.dto.request.UpdateMemberDto;
import org.mjulikelion.messengerapplication.encryption.PasswordHashEncryption;
import org.mjulikelion.messengerapplication.exception.AlreadyExistException;
import org.mjulikelion.messengerapplication.exception.ForbiddenException;
import org.mjulikelion.messengerapplication.exception.errorcode.ErrorCode;
import org.mjulikelion.messengerapplication.model.Member;
import org.mjulikelion.messengerapplication.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    //회원가입
    public void join(JoinDto joinDto){
        if(memberRepository.existsByEmail(joinDto.getEmail())){
            //이메일이 이미 존재할 경우
            throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 존재하는 이메일입니다.");
        }
        //비밀번호 암호화
        String encryptionPassword = passwordHashEncryption.encrypt(joinDto.getPassword());
        Member newMember = Member.builder()
                .name(joinDto.getName())
                .email(joinDto.getEmail())
                .password(encryptionPassword)
                .build();
        memberRepository.save(newMember);
    }

    //회원탈퇴
    public void deleteMember(Member member, DeleteMemberDto deleteMemberDto){
        //비밀번호 일치 여부 확인
        checkPassword(deleteMemberDto.getPassword(), member.getPassword());
        memberRepository.delete(member);
    }

    //회원정보 수정
    public void updateMember(Member member, UpdateMemberDto updateMemberDto){
        //비밀번호 일치 여부 확인
        checkPassword(updateMemberDto.getPassword(), member.getPassword());
        Member updateMember = memberRepository.findMemberById(member.getId());
        updateMember.setName(updateMemberDto.getName());
        //비밀번호 암호화
        String encryptionPassword = passwordHashEncryption.encrypt(updateMemberDto.getNewPassword());
        updateMember.setPassword(encryptionPassword);
        memberRepository.save(updateMember);
    }

    //로그인
    public Member login(LoginDto loginDto){
        Member member = memberRepository.findMemberByEmail(loginDto.getEmail());
        if(member!=null){
            //이메일에 해당하는 멤버가 존재
            if(passwordHashEncryption.matches(loginDto.getPassword(), member.getPassword())){
                //비밀번호 일치
                return member;
            }
            throw new ForbiddenException(ErrorCode.LOGIN_FALSE,"비밀번호 인증에 실패하였습니다.");
        }
        throw new ForbiddenException(ErrorCode.LOGIN_FALSE,"이메일 인증에 실패하였습니다.");
    }

    //비밀번호 일치 여부 확인
    public boolean checkPassword(String plainPassword, String hashedPassword){
        if(passwordHashEncryption.matches(plainPassword, hashedPassword)){
            return true;
        }throw new ForbiddenException(ErrorCode.NO_ACCESS, "비밀번호 정보가 일치하지 않습니다.");
    }
}
