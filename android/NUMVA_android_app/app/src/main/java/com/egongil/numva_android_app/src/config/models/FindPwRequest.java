package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class FindPwRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;


    public void setEmail(String email){this.email = email;}
    public void setPhone(String phone){this.phone = phone;}
}
