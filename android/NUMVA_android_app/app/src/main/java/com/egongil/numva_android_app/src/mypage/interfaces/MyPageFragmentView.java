package com.egongil.numva_android_app.src.mypage.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.mypage.models.LogoutResponse;

public interface MyPageFragmentView {
    void getLogoutSuccess(LogoutResponse logoutResponse, ErrorResponse errorResponse);
    void getLogoutFailure();
}
