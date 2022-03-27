package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.SaveQRCodeReqDto;
import com.egongil.numva.api.dto.request.UpdateSafetyInfoReqDto;
import com.egongil.numva.api.dto.response.FindSafetyInfoResDto;

import java.util.List;

public interface SafetyInfoService {
    List<FindSafetyInfoResDto> findAllSafetyInfo(String userEmail);
    List<FindSafetyInfoResDto> createSafetyInfo(String userEmail, SaveQRCodeReqDto reqDto);
    void updateSafetyInfo(Long safetyInfoId, UpdateSafetyInfoReqDto reqDto);
    void deleteSafetyInfo(Long safetyInfoId);
}
