package com.egongil.numva_android_app.src.signup.models;

import com.google.gson.annotations.SerializedName;

public class ValidEmailRequest {
    @SerializedName("email")
    private String email;

    public void setEmail(String email){
        this.email = email;
    }


}
