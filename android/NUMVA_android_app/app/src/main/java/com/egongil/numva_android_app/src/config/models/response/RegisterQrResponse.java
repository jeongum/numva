package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.SafetyInfo;
import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterQrResponse extends RetrofitResponse {

    @SerializedName("result")
    ArrayList<SafetyInfo> result;

    public ArrayList<SafetyInfo> getResult() {
        return result;
    }
}
