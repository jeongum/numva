package com.egongil.numva.api.controller;

import com.egongil.numva.api.dto.request.SaveQRCodeReqDto;
import com.egongil.numva.api.dto.request.UpdateSafetyInfoReqDto;
import com.egongil.numva.api.service.SafetyInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username="wjddma1214@gmail.com", password = "12345")
class SafetyInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyInfoService safetyInfoService;

    private final String userEmail = "wjddma1214@gmail.com";

    @Test
    void findAllSafetyInfo() throws Exception {
        // given
        // when
        mockMvc.perform(get("/api/safety-info"))
        // then
                .andExpect(status().isOk());

        verify(safetyInfoService, times(1)).findAllSafetyInfo(userEmail);
    }

    @Test
    void createSafetyInfo() throws Exception {
        // given
        SaveQRCodeReqDto reqDto = new SaveQRCodeReqDto("12345");

        // when
        mockMvc.perform(post("/api/safety-info")
                .content(asJsonString(reqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        // then
                .andExpect(status().isOk());
        verify(safetyInfoService, times(1)).createSafetyInfo(eq(userEmail), any(SaveQRCodeReqDto.class));
    }

    @Test
    void updateSafetyInfo() throws Exception {
        // given
        UpdateSafetyInfoReqDto reqDto = new UpdateSafetyInfoReqDto("이름변경");

        // when
        mockMvc.perform(put("/api/safety-info/{id}", 1L)
                .content(asJsonString(reqDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        // then
                .andExpect(status().isOk());
        verify(safetyInfoService, times(1)).updateSafetyInfo(eq(1L), any(UpdateSafetyInfoReqDto.class));
    }

    @Test
    void deleteSafetyInfo() throws Exception {
        // given
        // when
        mockMvc.perform(delete("/api/safety-info/{id}", 1L))
        // then
                .andExpect(status().isOk());
        verify(safetyInfoService, times(1)).deleteSafetyInfo(1L);
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}