package com.egongil.numva_android_app.src.config;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("user/user")
    Call<GetUserResponse> getUser();

    @GET("safetyInfo/getSI")
    Call<GetSafetyInfoResponse> getSafetyInfo();

    @GET("auth/logout")
    Call<LogoutResponse> getLogout();

    class GetUserResponse extends RetrofitResponse {
        @SerializedName("result")
        UserInfo result;

        public UserInfo getUser() {
            return result;
        }
    }

    class GetSafetyInfoResponse extends RetrofitResponse{
        @SerializedName("result")
        ArrayList<SafetyInfo> result;

        public ArrayList<SafetyInfo> getResult() {
            return result;
        }
    }

    class LogoutResponse extends RetrofitResponse{
    }

    class UserInfo {
        private int id;
        private String name;
        private String nickname;
        private String email;
        private String phone;
        private String second_phone = null;
        private String birth;

        public int getId() {
            return id;
        }

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

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSecond_phone(String second_phone) {
            this.second_phone = second_phone;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }
    }
    class SafetyInfo {
        int id;
        String name;
        String memo;
        String safety_number;

        public SafetyInfo(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getMemo() {
            return memo;
        }

        public String getSafety_number() {
            return safety_number;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
