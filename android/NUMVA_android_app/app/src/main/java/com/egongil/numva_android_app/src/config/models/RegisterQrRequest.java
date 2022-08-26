package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class RegisterQrRequest {
    @SerializedName("qr_id")
    String qr_id;

    public RegisterQrRequest(String qr_id) {
        this.qr_id = qr_id;
    }

    public void setQr_id(String qr_id) {
        this.qr_id = qr_id;
    }
}
