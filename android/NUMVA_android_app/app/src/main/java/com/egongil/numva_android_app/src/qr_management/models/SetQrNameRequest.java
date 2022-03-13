package com.egongil.numva_android_app.src.qr_management.models;

import com.google.gson.annotations.SerializedName;

public class SetQrNameRequest {

    @SerializedName("safety_info_id")
    int safety_info_id;

    @SerializedName("name")
    String name;

    public SetQrNameRequest(int safety_info_id, String name) {
        this.safety_info_id = safety_info_id;
        this.name = name;
    }

    public void setSafety_info_id(int safety_info_id) {
        this.safety_info_id = safety_info_id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
