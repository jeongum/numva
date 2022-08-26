package com.egongil.numva_android_app.src.config.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class GetUserResponse extends RetrofitResponse {
    @SerializedName("result")
    UserInfo result;

    public UserInfo getUser() {
        return result;
    }
}
