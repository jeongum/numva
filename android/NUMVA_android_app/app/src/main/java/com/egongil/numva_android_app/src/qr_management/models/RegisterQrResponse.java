package com.egongil.numva_android_app.src.qr_management.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterQrResponse extends RetrofitResponse {

    @SerializedName("result")
    ArrayList<RetrofitService.SafetyInfo> result;

    public ArrayList<RetrofitService.SafetyInfo> getResult() {
        return result;
    }
}
