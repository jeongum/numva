package com.egongil.numva_android_app.src.signup.interfaces;

import com.egongil.numva_android_app.src.signup.models.SignupRequest;
import com.egongil.numva_android_app.src.signup.models.SignupResponse;
import com.egongil.numva_android_app.src.signup.models.ValidEmailRequest;
import com.egongil.numva_android_app.src.signup.models.ValidEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupRetrofitInterface {
    @POST("auth/register")
    Call<SignupResponse> postSignup(
            @Body SignupRequest params);
    // 함수 postSignup을 호출하면 클래스(SignupResponse)의 객체로 결과를 반환한다.

    @POST("user/validEmail")
    Call<ValidEmailResponse> postValidEmail(
            @Body ValidEmailRequest params);
}
