package com.egongil.numva.core.entity.user;

import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.core.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Builder
    public User(String email, String name, String password, String nickname, String phone, LocalDate birth, Authority authority) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.birth = birth;
        this.authority = authority;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String phone;
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeInfo(String phone, String birth, String nickname) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.phone = phone;
        this.birth = LocalDate.parse(birth, formatter);
        this.nickname = nickname;
    }
}
