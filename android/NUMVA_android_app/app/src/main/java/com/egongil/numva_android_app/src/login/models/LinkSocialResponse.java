package com.egongil.numva_android_app.src.login.models;

import com.google.gson.annotations.SerializedName;

public class LinkSocialResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("result")
    private String access_token;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getMessage() {
        return message;
    }

}
