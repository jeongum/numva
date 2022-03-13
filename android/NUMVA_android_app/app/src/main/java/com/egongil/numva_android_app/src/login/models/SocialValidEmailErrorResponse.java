package com.egongil.numva_android_app.src.login.models;

import com.google.gson.annotations.SerializedName;

public class SocialValidEmailErrorResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private Result result;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

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
