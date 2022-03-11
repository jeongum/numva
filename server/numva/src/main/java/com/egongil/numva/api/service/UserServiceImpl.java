package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.response.UserResDto;
import com.egongil.numva.core.entity.user.User;
import com.egongil.numva.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.DuplicateFormatFlagsException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinReqDto reqDto) {
        userRepository.findByEmail(reqDto.getEmail()).ifPresent(
                m->{throw new IllegalArgumentException("이미 가입되어 있는 유저입니다.");
        });

        User user = reqDto.toEntity();

        user.changePassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserResDto getUserInfo(String email) {

        return null;
    }
}
