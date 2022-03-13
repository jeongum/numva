package com.egongil.numva_android_app.src.login.models;

import com.google.gson.annotations.SerializedName;

public class SocialValidEmailResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    //get
    public Boolean isSuccess() {
        return isSuccess;
    }
    public float getCode() { return code; }
    public String getMessage(){return message;}

    //set
    public void setIsSuccess(boolean success){this.isSuccess=success;}
    public void setCode(float code){this.code = code;}
    public void setMessage(String message){this.message=message;}

}
