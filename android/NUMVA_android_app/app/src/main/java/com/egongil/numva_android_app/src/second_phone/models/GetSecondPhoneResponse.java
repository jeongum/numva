package com.egongil.numva_android_app.src.second_phone.models;

import com.egongil.numva_android_app.src.main.models.GetUserResponse;
import com.egongil.numva_android_app.src.second_phone.SecondPhoneRecyclerItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSecondPhoneResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private ArrayList<SecondPhoneRecyclerItem> result;



    public boolean isSuccess(){return isSuccess;}
    public int getCode(){return code;}
    public String getMessage(){return message;}
    public ArrayList<SecondPhoneRecyclerItem> getResult(){return result;}



}

