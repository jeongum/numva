package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindEmailResDto;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    void craeteUser(){
        User user = User.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .phone("01087973122")
                .build();
        userRepository.save(user);
    }


    @Test
    void join() {
        // given
        JoinReqDto reqDto = JoinReqDto.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .birth("19971214")
                .phone("01087973122")
                .nickname("별명")
                .build();

        //when
        userService.join(reqDto);

        //then
        User savedUser = userRepository.findByEmail("wjddma1214@gmail.com").orElseThrow(IllegalStateException::new);
        assertThat(savedUser.getName()).isEqualTo("정음");
    }

    @Test
    void findUser() {
        //given
        craeteUser();

        //when
        FindUserResDto resDto = userService.findUser("wjddma1214@gmail.com");

        //then
        assertThat(resDto.getName()).isEqualTo("정음");
    }

    @Test
    void checkValidMail() {
        //given
        craeteUser();
        String email = "wjddma1214@gmail.com";

        //when
        BaseResponseEntity response = userService.checkValidMail(email);

        //then
        assertThat(response.getCode()).isEqualTo(-102);
    }

    @Test
    void findEmail() {
        //given
        craeteUser();
        String phone = "01087973122";
        String name = "정음";

        //when
        FindEmailResDto resDto = userService.findEmail(phone, name);

        //then
        assertThat(resDto.getEmail()).isEqualTo("wjddma1214@gmail.com");
    }

    @Test
    void modifyUser() {
        //given
        craeteUser();
        String email = "wjddma1214@gmail.com";
        UpdateUserReqDto reqDto = new UpdateUserReqDto("01012341234", "19971213", "별명2");

        //when
        userService.modifyUser(email, reqDto);

        //then
        User user = userRepository.findByEmail("wjddma1214@gmail.com").orElseThrow(IllegalStateException::new);
        assertThat(user.getPhone()).isEqualTo("01012341234");
        assertThat(user.getNickname()).isEqualTo("별명2");
    }
}