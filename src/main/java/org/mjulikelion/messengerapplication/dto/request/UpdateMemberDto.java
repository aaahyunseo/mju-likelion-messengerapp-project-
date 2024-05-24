package org.mjulikelion.messengerapplication.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateMemberDto {
    @NotNull(message = "원래 비밀번호가 누락되었습니다.")
    private String password;
    @NotNull(message = "변경할 이름이 누락되었습니다.")
    private String name;
    @NotNull(message = "변경할 비밀번호가 누락되었습니다.")
    private String newPassword;
}
