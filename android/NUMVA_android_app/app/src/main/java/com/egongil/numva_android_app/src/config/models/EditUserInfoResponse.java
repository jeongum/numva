package com.egongil.numva_android_app.src.config.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Result;

public class EditUserInfoResponse extends RetrofitResponse {
    @SerializedName("result")
    private Result result;

    public Result getResult(){return result;}
}
