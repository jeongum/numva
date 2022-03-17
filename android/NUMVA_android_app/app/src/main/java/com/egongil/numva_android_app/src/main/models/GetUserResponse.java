package com.egongil.numva_android_app.src.main.models;

import com.google.gson.annotations.SerializedName;

public class GetUserResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private float code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    Result result;

    // Getter Methods
    public boolean isSuccess() {
        return isSuccess;
    }

    public float getCode() {
        return code;
    }

    public Result getUser() {
        return result;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setName(String name) {
        this.result.name = name;
    }

    public void setEmail(String email) {
        this.result.email = email;
    }

    public void setPhone(String phone) {
        this.result.phone = phone;
    }

    public void setSecond_phone(String second_phone) {
        this.result.second_phone = second_phone;
    }

    public void setBirth(String birth) {
        this.result.birth = birth;
    }
    public void setNickname(String nickname) {
        this.result.nickname = nickname;
    }

    public class Result {
        private int id;
        private String name;
        private String nickname;
        private String email;
        private String phone;
        private String second_phone = null;
        private String birth;

        // Getter Methods
        public String getName() {
            return name;
        }

        public String getNickname() {
            return nickname;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getSecond_phone() {
            return second_phone;
        }

        public String getBirth() {
            return birth;
        }


    }
}
