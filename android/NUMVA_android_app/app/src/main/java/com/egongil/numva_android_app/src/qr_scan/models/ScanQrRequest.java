package com.egongil.numva_android_app.src.qr_scan.models;

import com.google.gson.annotations.SerializedName;

public class ScanQrRequest {
    @SerializedName("qr_id")
    String qr_id;

    public ScanQrRequest(String qr_id) {
        this.qr_id = qr_id;
    }
}
