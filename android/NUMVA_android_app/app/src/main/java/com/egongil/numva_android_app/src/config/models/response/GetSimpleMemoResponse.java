package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.egongil.numva_android_app.src.parkingmemo.model.SimpleMemoRecyclerItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSimpleMemoResponse extends RetrofitResponse {
    @SerializedName("result")
    private ArrayList<SimpleMemoRecyclerItem> result;

    public ArrayList<SimpleMemoRecyclerItem> getResult() {
        return result;
    }
}
