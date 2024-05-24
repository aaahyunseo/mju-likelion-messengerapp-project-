package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteMemberDto {
    @NotBlank(message = "비밀번호가 누락되었습니다.")
    private String password;
}
