package com.egongil.numva_android_app.src.config.models.request;

import com.google.gson.annotations.SerializedName;

public class DeleteSecondPhoneRequest {

    @SerializedName("second_phone_id")
    String id;

    public void setId(String id){this.id=id;}
}
