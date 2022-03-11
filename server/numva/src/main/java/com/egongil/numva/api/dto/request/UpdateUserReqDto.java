package com.egongil.numva.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserReqDto {
    String phone;
    Date birth;
    String nickname;
}
