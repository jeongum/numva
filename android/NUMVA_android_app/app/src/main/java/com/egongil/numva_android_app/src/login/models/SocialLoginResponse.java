package com.egongil.numva_android_app.src.login.models;

import com.google.gson.annotations.SerializedName;

public class SocialLoginResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private Result result;

    //get
    public Boolean isSuccess() {
        return isSuccess;
    }
    public float getCode() { return code; }
    public String getMessage(){return message;}

    public String getAccessToken() {
        return result.accessToken;
    }
    public String getMesiboToken() {
        return result.mesiboToken;
    }
    public class Result{
        String accessToken;
        String mesiboToken;
    }
}
