package com.egongil.numva.api.service;

import com.egongil.numva.api.dto.request.JoinReqDto;
import com.egongil.numva.api.dto.request.UpdateUserReqDto;
import com.egongil.numva.api.dto.response.BaseResponseEntity;
import com.egongil.numva.api.dto.response.FindEmailResDto;
import com.egongil.numva.api.dto.response.FindUserResDto;
import com.egongil.numva.core.entity.user.User;

public interface UserService {
    User join(JoinReqDto reqDto);
    FindUserResDto findUser(String email);
    BaseResponseEntity checkValidMail(String email);
    FindEmailResDto findEmail(String phone, String name);
    void modifyUser(String userEmail, UpdateUserReqDto reqDto);
}
