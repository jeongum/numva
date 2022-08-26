package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class LinkSocialResponse extends RetrofitResponse {
    @SerializedName("result")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }
}
