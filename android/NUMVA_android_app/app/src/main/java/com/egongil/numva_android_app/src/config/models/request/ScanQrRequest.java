package com.egongil.numva_android_app.src.config.models.request;

import com.google.gson.annotations.SerializedName;

public class ScanQrRequest {
    @SerializedName("qr_id")
    String qr_id;

    public ScanQrRequest(String qr_id) {
        this.qr_id = qr_id;
    }
}
