package com.egongil.numva_android_app.src.parkingmemo.models;

import com.google.gson.annotations.SerializedName;

public class AddSimpleMemoResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
