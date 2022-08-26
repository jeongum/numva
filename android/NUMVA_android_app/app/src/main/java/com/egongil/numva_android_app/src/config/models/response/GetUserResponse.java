package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.UserInfo;
import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class GetUserResponse extends RetrofitResponse {
    @SerializedName("result")
    UserInfo result;

    public UserInfo getUser() {
        return result;
    }
}
