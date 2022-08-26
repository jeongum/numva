package com.egongil.numva_android_app.src.config.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSafetyInfoResponse extends RetrofitResponse {
    @SerializedName("result")
    ArrayList<SafetyInfo> result;

    public ArrayList<SafetyInfo> getResult() {
        return result;
    }
}
