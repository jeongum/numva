package com.egongil.numva_android_app.src.findLogin.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class FindPwResponse extends RetrofitResponse {
    @SerializedName("result")
    private String result;
    public String getResult(){return result;}
}
