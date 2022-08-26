package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.LoginResponse;
import com.egongil.numva_android_app.src.config.models.SocialLoginResponse;
import com.egongil.numva_android_app.src.config.models.SocialValidEmailErrorResponse;
import com.egongil.numva_android_app.src.config.models.SocialValidEmailResponse;

public interface LoginActivityView {
    void postLoginSuccess(LoginResponse loginResponse, ErrorResponse errorResponse);

    void postLoginFailure();

    void isValidEmailSuccess(SocialValidEmailResponse validEmailResponse, SocialValidEmailErrorResponse errorResponse);

    void isValidEmailFailure();

    void socialLoginSucceess(SocialLoginResponse socialLoginResponse, ErrorResponse errorResponse);
    void socialLoginFailure();
}
