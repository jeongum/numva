package com.egongil.numva_android_app.src.config.models;

import com.google.gson.annotations.SerializedName;

public class LinkSocialRequest {
    @SerializedName("provider")
    String provider;

    @SerializedName("email")
    String email;

    @SerializedName("social_id")
    String social_id;

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }
}
