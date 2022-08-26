package com.egongil.numva_android_app.src.edit_userinfo.interfaces;

import com.egongil.numva_android_app.src.config.models.EditUserInfoRequest;
import com.egongil.numva_android_app.src.config.models.EditUserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EditUserInfoRetrofitInterface {
    @POST("user/edit")
    Call<EditUserInfoResponse> postEditUserInfo(
            @Body EditUserInfoRequest params);

}
