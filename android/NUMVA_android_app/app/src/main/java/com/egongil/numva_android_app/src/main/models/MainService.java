package com.egongil.numva_android_app.src.main.models;

import android.util.Log;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.main.interfaces.MainActivityView;
import com.egongil.numva_android_app.src.main.interfaces.MainRetrofitInterface;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.http.GET;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public interface MainService {
//    private final MainActivityView mMainActivityView;
//
//    public MainService(MainActivityView mMainActivityView){
//        this.mMainActivityView = mMainActivityView;
//    }
    @GET("user/user")
    Call<MainService.GetUserResponse> getUser();

    public class GetUserResponse {
        @SerializedName("isSuccess")
        private boolean isSuccess;

        @SerializedName("code")
        private float code;

        @SerializedName("message")
        private String message;

        @SerializedName("result")
        UserInfo result;

        // Getter Methods
        public boolean isSuccess() {
            return isSuccess;
        }

        public float getCode() {
            return code;
        }

        public UserInfo getUser() {
            return result;
        }

        public String getMessage() {
            return message;
        }
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
}