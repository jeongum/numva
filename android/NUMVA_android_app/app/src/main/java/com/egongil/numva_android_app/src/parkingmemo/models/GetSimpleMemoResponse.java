package com.egongil.numva_android_app.src.parkingmemo.models;

import com.egongil.numva_android_app.src.parkingmemo.SimpleMemoRecyclerItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSimpleMemoResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<SimpleMemoRecyclerItem> result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<SimpleMemoRecyclerItem> getResult() {
        return result;
    }
}
