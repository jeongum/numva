package com.egongil.numva_android_app.src.login.models;

public class SocialRegisterRequest {
    String name;
    String email;
    String phone;
    String birth;
    String nickname;
    String provider;
    String social_id;
    int agreement_marketing;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public void setAgreement_marketing(int agreement_marketing) {
        this.agreement_marketing = agreement_marketing;
    }
}
