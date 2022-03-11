package com.egongil.numva.api.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
