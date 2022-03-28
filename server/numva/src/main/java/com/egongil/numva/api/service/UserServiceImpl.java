package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindEmailResDto;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(JoinReqDto reqDto) {
        userRepository.findByEmail(reqDto.getEmail()).ifPresent(
                m->{throw new IllegalArgumentException("이미 가입되어 있는 유저입니다.");
        });

        User user = reqDto.toEntity();

        user.changePassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public FindUserResDto findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(IllegalStateException::new);

        return FindUserResDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .phone(user.getPhone())
                .name(user.getName())
                .birth(user.getBirth())
                .build();
    }

    @Override
    public BaseResponseEntity checkValidMail(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            return new BaseResponseEntity(false, -102);
        }
        return new BaseResponseEntity(true, 200);
    }

    @Override
    public FindEmailResDto findEmail(String phone, String name) {
        User user = userRepository.findByPhoneAndName(phone, name).orElseThrow(IllegalStateException::new);
        return new FindEmailResDto(user.getEmail());
    }

    @Override
    public void modifyUser(String email, UpdateUserReqDto reqDto) {
        User user = userRepository.findByEmail(email).orElseThrow(IllegalStateException::new);
        user.changeInfo(reqDto.getPhone(), reqDto.getBirth(), reqDto.getNickname());
    }
}
