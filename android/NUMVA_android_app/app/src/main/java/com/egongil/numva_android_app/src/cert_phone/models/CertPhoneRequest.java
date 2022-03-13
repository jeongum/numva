package com.egongil.numva_android_app.src.cert_phone.models;

import com.google.gson.annotations.SerializedName;

public class CertPhoneRequest {
    @SerializedName("phone_num")
    private String phone;

    @SerializedName("cert")
    private  String cert;

    public void setPhone(String phone){this.phone = phone;}
    public void setCert(String cert){this.cert = cert;}
}
