package com.egongil.numva_android_app.src.mypage.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;

public interface MyPageFragmentContract {
    void getLogoutSuccess(RetrofitService.LogoutResponse logoutResponse, ErrorResponse errorResponse);
    void getLogoutFailure();
}
