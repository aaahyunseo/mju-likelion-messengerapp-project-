package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotBlank(message = "이메일이 누락되었습니다.")
    @Email
    private String email;
    @NotBlank(message = "비밀번호가 누락되었습니다.")
    private String password;
}
