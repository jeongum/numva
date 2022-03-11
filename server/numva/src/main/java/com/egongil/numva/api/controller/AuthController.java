package com.egongil.numva.api.controller;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.request.LoginReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindEmailResDto;
import com.egongil.numva.api.dto.response.LoginResDto;
import com.egongil.numva.api.service.UserService;
import com.egongil.numva.jwt.JwtFilter;
import com.egongil.numva.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<BaseResponseEntity> join(@RequestBody JoinReqDto reqDto){
        userService.join(reqDto);
        return ResponseEntity.status(200).body(new BaseResponseEntity(true, 200));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto reqDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(reqDto.getEmail(), reqDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+jwt);

        return ResponseEntity.status(200).body(new LoginResDto(jwt, null));
    }

    @GetMapping("/valid-mail/{email}")
    public ResponseEntity<BaseResponseEntity> checkValidMail(@PathVariable String email){
        BaseResponseEntity response = userService.checkValidMail(email);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/find-mail/{phone}/{name}")
    public ResponseEntity<FindEmailResDto> findEmail(@PathVariable String phone, @PathVariable String name){
        FindEmailResDto resDto = userService.findEmail(phone, name);
        return ResponseEntity.status(200).body(resDto);
    }


}
