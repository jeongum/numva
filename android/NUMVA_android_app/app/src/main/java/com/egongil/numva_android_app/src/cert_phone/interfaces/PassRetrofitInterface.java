package com.egongil.numva_android_app.src.cert_phone.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.PassRequest;
import com.egongil.numva_android_app.src.cert_phone.models.PassResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface PassRetrofitInterface {
    @POST("pass/getInfo")
    Call<PassResponse> postPass(
            @Body PassRequest params);


}
