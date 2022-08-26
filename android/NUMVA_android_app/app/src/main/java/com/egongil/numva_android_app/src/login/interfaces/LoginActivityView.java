package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.LoginResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialLoginResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailResponse;

public interface LoginActivityView {
    void postLoginSuccess(LoginResponse loginResponse, ErrorResponse errorResponse);

    void postLoginFailure();

    void isValidEmailSuccess(SocialValidEmailResponse validEmailResponse, SocialValidEmailErrorResponse errorResponse);

    void isValidEmailFailure();

    void socialLoginSucceess(SocialLoginResponse socialLoginResponse, ErrorResponse errorResponse);
    void socialLoginFailure();
}
