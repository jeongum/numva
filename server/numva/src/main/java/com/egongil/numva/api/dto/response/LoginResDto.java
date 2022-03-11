package com.egongil.numva.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {
    private String accessToken;
    private String mesiboToken;
}
