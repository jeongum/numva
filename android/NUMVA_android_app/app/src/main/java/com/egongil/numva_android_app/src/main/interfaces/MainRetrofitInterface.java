package com.egongil.numva_android_app.src.main.interfaces;


import com.egongil.numva_android_app.src.main.models.MainService;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainRetrofitInterface {
    @GET("user/user")
    Call<MainService.GetUserResponse> getUser();
}
