package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.login.models.LoginRequest;
import com.egongil.numva_android_app.src.login.models.LoginResponse;
import com.egongil.numva_android_app.src.login.models.SocialLoginRequest;
import com.egongil.numva_android_app.src.login.models.SocialLoginResponse;
import com.egongil.numva_android_app.src.login.models.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.signup.models.ValidEmailRequest;
import com.egongil.numva_android_app.src.signup.models.ValidEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("auth/login")
    Call<LoginResponse> postLogin(@Body LoginRequest params);

    @POST("user/socialValidEmail")
    Call<SocialValidEmailResponse> isValidEmail(@Body ValidEmailRequest params);

    @POST("auth/socialLogin")
    Call<SocialLoginResponse> socialLogin(@Body SocialLoginRequest params);
}
