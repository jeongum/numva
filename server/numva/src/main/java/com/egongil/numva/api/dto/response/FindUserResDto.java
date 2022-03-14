package com.egongil.numva.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserResDto{
    @Builder
    public FindUserResDto(boolean isSuccess, int code, Long id, String email, String name, String phone, LocalDate birth) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
    }

    Long id;
    String email;
    String name;
    String phone;
    LocalDate birth;
}
