package com.egongil.numva_android_app.src.config.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.egongil.numva_android_app.src.parkingmemo.SimpleMemoRecyclerItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSimpleMemoResponse extends RetrofitResponse {
    @SerializedName("result")
    private ArrayList<SimpleMemoRecyclerItem> result;

    public ArrayList<SimpleMemoRecyclerItem> getResult() {
        return result;
    }
}
