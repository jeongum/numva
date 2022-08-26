package com.egongil.numva_android_app.src.mypage.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.LogoutResponse;

public interface MyPageFragmentContract {
    void getLogoutSuccess(LogoutResponse logoutResponse, ErrorResponse errorResponse);
    void getLogoutFailure();
}
