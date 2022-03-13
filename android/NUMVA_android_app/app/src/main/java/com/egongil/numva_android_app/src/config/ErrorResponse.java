package com.egongil.numva_android_app.src.config;

public class ErrorResponse {
    private boolean isSuccess;
    private int code;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
