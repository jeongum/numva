package com.egongil.numva_android_app.src.customer_center.models;

import com.egongil.numva_android_app.src.customer_center.FAQRecyclerItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FAQResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<FAQRecyclerItem> result;

    public boolean isSuccess(){return isSuccess;}
    public int getCode(){return code;}
    public String getMessage(){return message;}
    public ArrayList<FAQRecyclerItem> getResult(){return result;}


}
