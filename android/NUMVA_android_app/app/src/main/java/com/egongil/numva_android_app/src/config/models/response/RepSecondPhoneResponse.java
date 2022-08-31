package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;

public class RepSecondPhoneResponse extends RetrofitResponse {
    SecondResult result;

    public String getSecondPhone(){
        return result.second_phone;
    }

    class SecondResult{
        int user_id;
        String second_phone;
    }
}
