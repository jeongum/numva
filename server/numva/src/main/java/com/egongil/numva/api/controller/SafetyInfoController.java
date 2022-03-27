package com.egongil.numva.api.controller;

import com.egongil.numva.api.dto.request.SaveQRCodeReqDto;
import com.egongil.numva.api.dto.request.UpdateSafetyInfoReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindSafetyInfoResDto;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.api.service.SafetyInfoService;
import com.egongil.numva.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/safety-info")
@RequiredArgsConstructor
public class SafetyInfoController {

    private final SafetyInfoService safetyInfoService;

    @GetMapping
    public ResponseEntity<List<FindSafetyInfoResDto>> findAllSafetyInfo(){
        String userEmail = SecurityUtil.getCurrentUsername().orElseThrow(IllegalStateException::new);
        List<FindSafetyInfoResDto> response= safetyInfoService.findAllSafetyInfo(userEmail);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<List<FindSafetyInfoResDto>> createSafetyInfo(@RequestBody @Valid SaveQRCodeReqDto reqDto){
        String userEmail = SecurityUtil.getCurrentUsername().orElseThrow(IllegalStateException::new);
        List<FindSafetyInfoResDto> response = safetyInfoService.createSafetyInfo(userEmail, reqDto);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseEntity> updateSafetyInfo(@PathVariable Long id, @RequestBody @Valid UpdateSafetyInfoReqDto reqDto){
        safetyInfoService.updateSafetyInfo(id, reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(true, 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseEntity> deleteSafetyInfo(@PathVariable Long id){
        safetyInfoService.deleteSafetyInfo(id);
        return ResponseEntity.status(200).body(new BaseResponseEntity(true, 200));
    }
}
