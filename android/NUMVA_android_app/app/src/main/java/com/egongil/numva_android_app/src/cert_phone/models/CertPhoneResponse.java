package com.egongil.numva_android_app.src.cert_phone.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class CertPhoneResponse extends RetrofitResponse {
    @SerializedName("result")
    private String result;

    public String getResult(){return result;}
}
