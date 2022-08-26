package com.egongil.numva_android_app.src.config;

import com.egongil.numva_android_app.src.config.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.GetUserResponse;
import com.egongil.numva_android_app.src.config.models.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("user/user")
    Call<GetUserResponse> getUser();

    @GET("safetyInfo/getSI")
    Call<GetSafetyInfoResponse> getSafetyInfo();

    @GET("auth/logout")
    Call<LogoutResponse> getLogout();

}
