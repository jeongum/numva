package com.egongil.numva_android_app.src.parkingmemo.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class GetParkingMemoResponse extends RetrofitResponse {
    @SerializedName("result")
    ParkingMemo result;

    public ParkingMemo getResult() {
        return result;
    }
}
