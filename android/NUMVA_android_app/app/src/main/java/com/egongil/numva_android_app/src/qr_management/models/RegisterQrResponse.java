package com.egongil.numva_android_app.src.qr_management.models;

import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterQrResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    ArrayList<SafetyInfo> result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<SafetyInfo> getResult() {
        return result;
    }
}
