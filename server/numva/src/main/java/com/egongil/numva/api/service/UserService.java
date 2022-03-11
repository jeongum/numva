package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.response.UserResDto;

public interface UserService {
    public void join(JoinReqDto reqDto);
    public UserResDto getUserInfo(String email);
}
