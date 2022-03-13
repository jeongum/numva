package com.egongil.numva_android_app.src.parkingmemo.models;

import com.google.gson.annotations.SerializedName;

public class GetParkingMemoResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    ParkingMemo result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ParkingMemo getResult() {
        return result;
    }
}
