package com.egongil.numva_android_app.src.config.models.request;

import com.google.gson.annotations.SerializedName;

public class AddSimpleMemoRequest {
    @SerializedName("memo")
    String memo;

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
