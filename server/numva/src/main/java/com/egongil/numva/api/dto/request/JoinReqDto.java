package com.egongil.numva.api.dto.request;

import com.egongil.numva.core.entity.user.Authority;
import com.egongil.numva.core.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinReqDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private String nickname;
    private String phone;
    private String birth;



    public User toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .birth(LocalDate.parse(birth, formatter))
                .authority(Authority.ROLE_USER)
                .build();
    }
}
