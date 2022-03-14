package com.egongil.numva.api.controller;

import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.api.service.UserService;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username="wjddma1214@gmail.com", password = "12345")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void before(){
        User user = User.builder()
                .email("wjddma1214@gmail.com")
                .password("12345")
                .name("정음")
                .phone("01087973122")
                .build();
        userRepository.save(user);
    }

    @Test
    void findUser() throws Exception {
        //given
        //when
        mockMvc.perform(get("/api/user"))

        //then
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService, times(1)).findUser("wjddma1214@gmail.com");
    }

    @Test
    void modifyUser() throws Exception {
        // given
        UpdateUserReqDto reqDto = new UpdateUserReqDto("01087973122","19971214","닉");

        //when
        mockMvc.perform(put("/api/user")
                .content(asJsonString(reqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        //then
                .andExpect(status().isOk());
        verify(userService, times(1)).modifyUser("wjddma1214@gmail.com", reqDto);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}