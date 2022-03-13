package com.egongil.numva_android_app.src.qr_management.models;

import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSafetyInfoResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    ArrayList<SafetyInfo> result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<SafetyInfo> getResult() {
        return result;
    }
}
