package com.egongil.numva_android_app.src.findLogin.models;

import com.google.gson.annotations.SerializedName;

public class FindPwResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private String result;


    public boolean isSuccess(){return isSuccess;}
    public float getCode(){return code;}
    public String getMessage(){return message;}
    public String getResult(){return result;}

}
