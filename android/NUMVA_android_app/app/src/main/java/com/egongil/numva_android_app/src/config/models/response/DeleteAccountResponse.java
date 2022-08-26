package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Result;

public class DeleteAccountResponse extends RetrofitResponse {
    @SerializedName("result")
    private Result result;
    public Result getResult(){return result;}
}
