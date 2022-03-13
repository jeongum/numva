package com.egongil.numva_android_app.src.parkingmemo.models;

import com.google.gson.annotations.SerializedName;

public class EditSimpleMemoRequest {
    @SerializedName("quickmemo_id")
    int quickmemo_id;

    @SerializedName("memo")
    String memo;

    public void setQuickmemo_id(int quickmemo_id) {
        this.quickmemo_id = quickmemo_id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
