package com.egongil.numva_android_app.src.mypage.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.LogoutResponse;

public interface MyPageFragmentContract {
    void getLogoutSuccess(LogoutResponse logoutResponse, ErrorResponse errorResponse);
    void getLogoutFailure();
}
