package com.egongil.numva_android_app.src.config.models.request;

import com.google.gson.annotations.SerializedName;

public class DeleteQrRequest {
    @SerializedName("safety_info_id")
    int safety_info_id;

    public DeleteQrRequest(int safety_info_id) {
        this.safety_info_id = safety_info_id;
    }

    public void setSafety_info_id(int safety_info_id) {
        this.safety_info_id = safety_info_id;
    }
}
