package com.egongil.numva_android_app.src.signup.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.SignupResponse;
import com.egongil.numva_android_app.src.config.models.response.ValidEmailResponse;

public interface SignupActivityView {

    //SignupActivity에서 implements해서 사용할 인터페이스

    //회원가입 post성공 시
    void postSignupSuccess(SignupResponse signupResponse, ErrorResponse errorResponse);

    //회원가입 post실패 시
    void postSignupFailure();


    //email 중복확인 post 성공 시
    void postValidEmailSuccess(ValidEmailResponse validEmailResponse, ErrorResponse errorResponse);

    //email 중복확인 post 실패 시시
    void postValidEmailFailure();
}
