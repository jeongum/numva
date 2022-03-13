package com.egongil.numva_android_app.src.mypage.interfaces;

import com.egongil.numva_android_app.src.mypage.models.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyPageRetrofitInterface {
    @GET("auth/logout")
    Call<LogoutResponse> getLogout();
}
