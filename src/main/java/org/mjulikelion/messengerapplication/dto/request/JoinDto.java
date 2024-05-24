package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class JoinDto {
    @NotBlank(message = "이름이 누락되었습니다.")
    private String name;    //멤버 이름
    @Email
    @NotBlank(message = "이메일이 누락되었습니다.")
    private String email;   //멤버 이메일
    @NotBlank(message = "비밀번호가 누락되었습니다.")
    private String password;    //멤버 비밀번호
}
