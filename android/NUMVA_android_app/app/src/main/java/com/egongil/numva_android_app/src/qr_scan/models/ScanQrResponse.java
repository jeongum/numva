package com.egongil.numva_android_app.src.qr_scan.models;

import com.google.gson.annotations.SerializedName;

public class ScanQrResponse {
    @SerializedName("isSuccess")
    boolean isSuccess;

    @SerializedName("code")
    int code;

    @SerializedName("message")
    String message;

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

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ScanResult getResult() {
        return result;
    }
}
