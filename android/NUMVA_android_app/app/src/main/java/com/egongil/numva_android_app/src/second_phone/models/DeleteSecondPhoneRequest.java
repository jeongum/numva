package com.egongil.numva_android_app.src.second_phone.models;

import com.google.gson.annotations.SerializedName;

public class DeleteSecondPhoneRequest {

    @SerializedName("second_phone_id")
    String id;

    public void setId(String id){this.id=id;}
}
