package com.egongil.numva_android_app.src.cert_phone.models;

import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class PassResponse extends RetrofitResponse {
    @SerializedName("result")
    private PassResult passResult;

    public PassResult getPassResult(){return passResult;}

    public class PassResult{
        private String imp_uid;
        private String merchant_uid;
        private String pg_tid;
        private String pg_provider;
        private String name;
        private String gender;
        private Integer birth;
        private String birthday;
        private boolean foreigner;
        private String phone;
        private String carrier;
        private boolean certified;
        private Integer certified_at;
        private String unique_key;
        private String unique_in_site;
        private String origin;

        public String getName(){return name;}
        public String getBirthday(){return birthday;}
        public String getPhone(){return phone;}
    }
}
