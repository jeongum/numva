package com.egongil.numva_android_app.src.home.interfaces;

import com.egongil.numva_android_app.src.home.models.GetSafetyInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeRetrofitInterface {
    @GET("safetyInfo/getSI")
    Call<GetSafetyInfoResponse> getSafetyInfo();
}
