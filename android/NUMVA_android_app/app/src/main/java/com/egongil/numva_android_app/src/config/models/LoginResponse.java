package com.egongil.numva_android_app.src.config.models;
import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends RetrofitResponse {
    @SerializedName("result")
    private Result result;

    public String getAccessToken() {
        return result.accessToken;
    }

    public String getMesiboToken() { return result.mesiboToken;}

    public class Result{
        String accessToken;
        String mesiboToken;
    }
}
