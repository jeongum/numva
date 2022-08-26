package com.egongil.numva_android_app.src.customer_center.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.egongil.numva_android_app.src.customer_center.FAQRecyclerItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FAQResponse extends RetrofitResponse {
    @SerializedName("result")
    private ArrayList<FAQRecyclerItem> result;
    public ArrayList<FAQRecyclerItem> getResult(){return result;}
}
