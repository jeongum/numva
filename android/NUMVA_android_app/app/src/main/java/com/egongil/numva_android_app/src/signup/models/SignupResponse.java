package com.egongil.numva_android_app.src.signup.models;

import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Result;


public class SignupResponse {


    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private float code;


    @SerializedName("message")
    private String message;

////////////////////get
    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


/////////////////////set
    public void setCode(float code){this.code =code;}

    public void setMessage(String message){this.message = message;}

    public void setIsSuccess(boolean success){this.isSuccess=success;}


}
