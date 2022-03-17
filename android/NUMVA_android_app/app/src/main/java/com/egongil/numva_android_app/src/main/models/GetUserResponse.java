package com.egongil.numva_android_app.src.main.models;

import com.google.gson.annotations.SerializedName;

public class GetUserResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    UserInfo result;

    // Getter Methods
    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public UserInfo getUser() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
