package com.egongil.numva_android_app.src.config.models.request;

public class SocialLoginRequest {
    String provider;
    String social_id;

    public SocialLoginRequest(String provider, String social_id) {
        this.provider = provider;
        this.social_id = social_id;
    }
}
