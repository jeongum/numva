package com.egongil.numva_android_app.src.second_phone.models;

import com.google.gson.annotations.SerializedName;

public class SetSecondPhoneResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public boolean isSuccess(){return isSuccess;}
    public int getCode(){return code;}
    public String getMessage(){return message;}
}
