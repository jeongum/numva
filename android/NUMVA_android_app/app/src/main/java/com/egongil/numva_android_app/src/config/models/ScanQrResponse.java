package com.egongil.numva_android_app.src.config.models;

import com.egongil.numva_android_app.src.config.RetrofitResponse;
import com.google.gson.annotations.SerializedName;

public class ScanQrResponse extends RetrofitResponse {
    @SerializedName("result")
    ScanResult result;

    public class ScanResult{
        String nickname;
        String memo;
        SafetyNumber safetyNumber;
        String mesibo_address;

        public String getNickname() {
            return nickname;
        }

        public String getMemo() {
            return memo;
        }

        public SafetyNumber getSafetyNumber() {
            return safetyNumber;
        }

        public String getMesibo_address() {
            return mesibo_address;
        }
    }

    public class SafetyNumber{
        String first;
        String second;

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }
    }
    public ScanResult getResult() {
        return result;
    }
}
