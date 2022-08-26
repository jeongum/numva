package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("phone")
    private String phone;

    @SerializedName("birth")
    private String birth;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("agreement_marketing")
    private int agreement_marketing;

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setBirth(String birth){
        this.birth = birth;
    }

    public void setNickname(String nickname){this.nickname = nickname;}

    public void setAgreement_marketing(int agreement_marketing){this.agreement_marketing = agreement_marketing;}



    public void getEmail(String email) {this.email = email;}
}
