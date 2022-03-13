package com.egongil.numva_android_app.src.edit_userinfo.models;

import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Result;

public class DeleteAccountResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private Result result;

    public boolean isSuccess(){return isSuccess;}
    public float getCode(){return code;}
    public String getMessage(){return message;}
    public Result getResult(){return result;}
}
