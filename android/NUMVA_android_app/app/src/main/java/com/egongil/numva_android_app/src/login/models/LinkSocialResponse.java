package com.egongil.numva_android_app.src.login.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class LinkSocialResponse extends RetrofitResponse {
    @SerializedName("result")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }
}
