package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindEmailResDto;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final String userEmail = "wjddma1214@gmail.com";

    void createUser(){
        User user = User.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .phone("01087973122")
                .build();
        given(userRepository.findByEmail(userEmail)).willReturn(Optional.of(user));
    }


    @Test
    void join() {
        // given
        User user = User.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .phone("01087973122")
                .build();

        JoinReqDto reqDto = JoinReqDto.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .birth("19971214")
                .phone("01087973122")
                .nickname("별명")
                .build();

        given(userRepository.save(any())).willReturn(user);
        //when
        User savedUser = userService.join(reqDto);

        //then
        assertThat(savedUser.getName()).isEqualTo("정음");
    }

    @Test
    void findUser() {
        //given
        createUser();

        //when
        FindUserResDto resDto = userService.findUser(userEmail);

        //then
        assertThat(resDto.getName()).isEqualTo("정음");
    }

    @Test
    void checkValidMail() {
        //given
        createUser();

        //when
        BaseResponseEntity response = userService.checkValidMail(userEmail);

        //then
        assertThat(response.getCode()).isEqualTo(-102);
    }

    @Test
    void findEmail() {
        //given
        User user = User.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .phone("01087973122")
                .build();
        given(userRepository.findByPhoneAndName(any(), any())).willReturn(Optional.of(user));

        //when
        FindEmailResDto resDto = userService.findEmail("01087973122", "정음");

        //then
        assertThat(resDto.getEmail()).isEqualTo("wjddma1214@gmail.com");
    }

    @Test
    void modifyUser() {
        //given
        createUser();
        UpdateUserReqDto reqDto = new UpdateUserReqDto("01012341234", "19971213", "별명2");

        //when
        userService.modifyUser(userEmail, reqDto);

        //then
        User user = userRepository.findByEmail("wjddma1214@gmail.com").orElseThrow(IllegalStateException::new);
        assertThat(user.getPhone()).isEqualTo("01012341234");
        assertThat(user.getNickname()).isEqualTo("별명2");
    }
}