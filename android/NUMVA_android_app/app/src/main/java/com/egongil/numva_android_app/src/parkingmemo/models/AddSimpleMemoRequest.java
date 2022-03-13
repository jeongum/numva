package com.egongil.numva_android_app.src.parkingmemo.models;

import com.google.gson.annotations.SerializedName;

public class AddSimpleMemoRequest {
    @SerializedName("memo")
    String memo;

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
