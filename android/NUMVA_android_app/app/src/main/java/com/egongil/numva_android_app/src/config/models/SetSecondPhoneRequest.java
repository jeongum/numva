package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class SetSecondPhoneRequest {

    @SerializedName("second_phone")
    String secondphone;


    public void setSecond_phone(String secondphone){this.secondphone = secondphone;}

}
