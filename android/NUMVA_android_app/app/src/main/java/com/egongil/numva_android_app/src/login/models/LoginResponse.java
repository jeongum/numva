package com.egongil.numva_android_app.src.login.models;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("result")
    private Result result;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getAccessToken() {
        return result.accessToken;
    }

    public String getMesiboToken() { return result.mesiboToken;}

    public String getMessage() {
        return message;
    }

    public class Result{
        String accessToken;
        String mesiboToken;
    }
}
