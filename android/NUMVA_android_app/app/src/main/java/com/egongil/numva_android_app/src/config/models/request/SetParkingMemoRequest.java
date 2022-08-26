package com.egongil.numva_android_app.src.config.models.request;

import com.google.gson.annotations.SerializedName;

public class SetParkingMemoRequest {
    @SerializedName("memo")
    String memo;

    @SerializedName("safety_info_id")
    int safety_info_id;

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setSafety_info_id(int safety_info_id) {
        this.safety_info_id = safety_info_id;
    }
}
