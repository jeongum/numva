package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.config.models.LinkSocialRequest;
import com.egongil.numva_android_app.src.config.models.LinkSocialResponse;
import com.egongil.numva_android_app.src.config.models.SocialRegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SnsLoginRetrofitInterface {
    @POST("auth/linkSocial")
    Call<LinkSocialResponse> linkSocial(@Body LinkSocialRequest params);

    @POST("auth/socialRegister")
    Call<LinkSocialResponse> socialRegister(@Body SocialRegisterRequest params);
}
