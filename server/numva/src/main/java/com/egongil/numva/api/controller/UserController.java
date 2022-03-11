package com.egongil.numva.api.controller;

import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.api.service.UserService;
import com.egongil.numva.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<FindUserResDto> findUser(){
        String userEmail = getUsername(SecurityUtil.getCurrentUsername());
        FindUserResDto resDto = userService.findUser(userEmail);
        return ResponseEntity.status(200).body(resDto);
    }

    @PutMapping
    public ResponseEntity<BaseResponseEntity> modifyUser(@RequestBody UpdateUserReqDto reqDto){
        String userEmail = getUsername(SecurityUtil.getCurrentUsername());
        userService.modifyUser(userEmail, reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(true, 200));
    }

    public String getUsername(Optional<String> username){
        return username.orElseThrow(IllegalStateException::new);
    }
}
