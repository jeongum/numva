package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.SaveQRCodeReqDto;
import com.egongil.numva.api.dto.request.UpdateSafetyInfoReqDto;
import com.egongil.numva.api.dto.response.FindSafetyInfoResDto;
import com.egongil.numva.core.entity.SafetyInfo.QRCode;
import com.egongil.numva.core.entity.SafetyInfo.SafetyInfo;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.queryrepository.SafetyInfoQueryRepository;
import com.egongil.numva.core.repository.SafetyInfoRepository;
import com.egongil.numva.core.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SafetyInfoServiceTest {
    @InjectMocks
    private SafetyInfoServiceImpl safetyInfoService;

    @Mock
    private SafetyInfoRepository safetyInfoRepository;

    @Mock
    private UserRepository userRepository;


    @Mock
    private SafetyInfoQueryRepository safetyInfoQueryRepository;

    final String userEmail = "wjddma1214@gmail.com";

    void setup(){
        User user = User.builder().build();
        given(userRepository.findByEmail(userEmail)).willReturn(Optional.of(user));
    }

    @Test
    void findAllSafetyInfo() {
        // given
        setup();
        List<FindSafetyInfoResDto> expected = new ArrayList<>();
        given(safetyInfoQueryRepository.findAllWithUserId(any())).willReturn(expected);

        // when
        List<FindSafetyInfoResDto> response = safetyInfoService.findAllSafetyInfo(userEmail);

        // then
        assertThat(response).isEqualTo(expected);
    }

    @Test
    void createSafetyInfo() {
        // given
        setup();
        List<FindSafetyInfoResDto> expected = new ArrayList<>();
        SaveQRCodeReqDto reqDto = new SaveQRCodeReqDto("123456");
        SafetyInfo safetyInfo = SafetyInfo.builder().build();
        QRCode qrCode = new QRCode("123456");
        safetyInfo.setQRCode(qrCode);

        given(safetyInfoRepository.save(any())).willReturn(null);
        given(safetyInfoQueryRepository.findAllWithUserId(any())).willReturn(expected);

        // when
        List<FindSafetyInfoResDto> response = safetyInfoService.createSafetyInfo(userEmail, reqDto);

        // then
        assertEquals(response, expected);
    }

    @Test
    void updateSafetyInfo() {
        // given
        SafetyInfo safetyInfo = SafetyInfo.builder().build();
        QRCode qrCode = new QRCode("123456");
        safetyInfo.setQRCode(qrCode);

        given(safetyInfoRepository.findById(any())).willReturn(Optional.of(safetyInfo));

        UpdateSafetyInfoReqDto reqDto = new UpdateSafetyInfoReqDto("Hi");
        // when
        safetyInfoService.updateSafetyInfo(1L, reqDto);

        // then
        assertEquals(safetyInfo.getName(), reqDto.getName());
    }
}