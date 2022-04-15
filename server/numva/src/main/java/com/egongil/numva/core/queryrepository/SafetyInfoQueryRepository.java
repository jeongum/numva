package com.egongil.numva.core.queryrepository;

import com.egongil.numva.api.dto.response.FindSafetyInfoResDto;

import java.util.List;

public interface SafetyInfoQueryRepository {
    List<FindSafetyInfoResDto> findAllWithUserId(Long userId);
    FindSafetyInfoResDto findWithQRCode(String code);
}
