package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.UserInfo;
import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class EditUserInfoResponse extends RetrofitResponse {
    @SerializedName("result")
    private UserInfo result;

    public UserInfo getResult(){return result;}
}
