package com.egongil.numva_android_app.src.config.models;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class SocialValidEmailErrorResponse extends ErrorResponse {
    @SerializedName("result")
    private Result result;

    public String getProvider() {
        return result.provider;
    }
    public String getSocialId(){
        return result.social_id;
    }

    public class Result{
        @SerializedName("provider")
        private String provider;

        @SerializedName("social_id")
        private String social_id;
    }
}
