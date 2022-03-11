package com.egongil.numva.api.dto.request;

import com.egongil.numva.core.entity.user.Authority;
import com.egongil.numva.core.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinReqDto {
    private String email;
    private String password;
    private String name;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
