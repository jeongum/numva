package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class FindIdRequest {
    @SerializedName("phone")
    private String phone;

    @SerializedName("name")
    private String name;

    public void setPhone(String phone){this.phone = phone;}
    public void setName(String name){this.name = name;}
}
