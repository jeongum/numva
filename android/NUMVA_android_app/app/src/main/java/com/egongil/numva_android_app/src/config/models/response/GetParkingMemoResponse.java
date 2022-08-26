package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.ParkingMemo;
import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class GetParkingMemoResponse extends RetrofitResponse {
    @SerializedName("result")
    ParkingMemo result;

    public ParkingMemo getResult() {
        return result;
    }
}
