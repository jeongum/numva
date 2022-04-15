package com.egongil.numva.api.service;


import com.egongil.numva.api.dto.request.SaveQRCodeReqDto;
import com.egongil.numva.api.dto.request.UpdateSafetyInfoReqDto;
import com.egongil.numva.api.dto.response.FindSafetyInfoResDto;
import com.egongil.numva.core.entity.SafetyInfo.Memo;
import com.egongil.numva.core.entity.SafetyInfo.QRCode;
import com.egongil.numva.core.entity.SafetyInfo.SafetyInfo;
import com.egongil.numva.core.entity.SafetyInfo.SafetyNumber;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.queryrepository.SafetyInfoQueryRepository;
import com.egongil.numva.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SafetyInfoServiceImpl implements SafetyInfoService {

    private final SafetyInfoRepository safetyInfoRepository;
    private final SafetyInfoQueryRepository safetyInfoQueryRepository;
    private final UserRepository userRepository;

    @Override
    public List<FindSafetyInfoResDto> findAllSafetyInfo(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(()->new IllegalStateException());
        return safetyInfoQueryRepository.findAllWithUserId(user.getId());
    }

    @Override
    public List<FindSafetyInfoResDto> createSafetyInfo(String userEmail, SaveQRCodeReqDto reqDto) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(()->new IllegalStateException());
        QRCode qr = new QRCode(reqDto.getQrCode());
        Memo memo = new Memo("");
        SafetyNumber safetyNumber= new SafetyNumber(user.getPhone());

        SafetyInfo safetyInfo = new SafetyInfo(reqDto.getQrCode());

        safetyInfo.setUser(user);
        safetyInfo.setQRCode(qr);
        safetyInfo.setMemo(memo);
        safetyInfo.setSafetyNumber(safetyNumber);

        safetyInfoRepository.save(safetyInfo);

        return safetyInfoQueryRepository.findAllWithUserId(user.getId());
    }

    @Override
    public void updateSafetyInfo(Long safetyInfoId, UpdateSafetyInfoReqDto reqDto) {
        SafetyInfo safetyInfo = safetyInfoRepository.findById(safetyInfoId).orElseThrow(()->new IllegalStateException());
        safetyInfo.changeName(reqDto.getName());
    }

    @Override
    public void deleteSafetyInfo(Long safetyInfoId){
        SafetyInfo safetyInfo = safetyInfoRepository.findById(safetyInfoId).orElseThrow(()->new IllegalStateException());
        safetyInfoRepository.delete(safetyInfo);
    }

    @Override
    public FindSafetyInfoResDto findSafetyInfo(String code) {
        return safetyInfoQueryRepository.findWithQRCode(code);
    }
}
