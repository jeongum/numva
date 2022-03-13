package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.login.models.LoginResponse;
import com.egongil.numva_android_app.src.login.models.SocialLoginResponse;
import com.egongil.numva_android_app.src.login.models.SocialValidEmailErrorResponse;
import com.egongil.numva_android_app.src.login.models.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.signup.models.ValidEmailResponse;

public interface LoginActivityView {
    void postLoginSuccess(LoginResponse loginResponse, ErrorResponse errorResponse);

    void postLoginFailure();

    void isValidEmailSuccess(SocialValidEmailResponse validEmailResponse, SocialValidEmailErrorResponse errorResponse);

    void isValidEmailFailure();

    void socialLoginSucceess(SocialLoginResponse socialLoginResponse, ErrorResponse errorResponse);
    void socialLoginFailure();
}
