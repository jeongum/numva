package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class DeleteSimpleMemoRequest {
    @SerializedName("quickmemo_id")
    int quickmemo_id;

    public void setQuickmemo_id(int quickmemo_id) {
        this.quickmemo_id = quickmemo_id;
    }
}
