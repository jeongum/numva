package com.egongil.numva_android_app.src.login.snslogin.naverlogin;

import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_BIRTHDAY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_BIRTHYEAR;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_EMAIL;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_ID;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_NAME;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_PHONE;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class NaverLogin extends Activity {
    final String NAVER_RESPONSE_CODE = "00"; //정상 반환 시 코드
    final String[] NAVER_JSON_KEY = {"id", "name", "email", "mobile", "birthyear", "birthday"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OAuthLogin mNaverLoginModule = OAuthLogin.getInstance();

        String accessToken = mNaverLoginModule.getAccessToken(getApplicationContext());

        if (accessToken != null && OAuthLoginState.OK.equals(mNaverLoginModule.getState(getApplicationContext()))) {
            ReqNHNUserInfo reqNHNUserInfo = new ReqNHNUserInfo();
            reqNHNUserInfo.execute(accessToken);
        } else{
            RefreshNHNToken tokenRefresh = new RefreshNHNToken();
            try{
                tokenRefresh.execute().get();
            }catch(Exception e){
                e.printStackTrace();
            }
            ReqNHNUserInfo reqNaverUserInfo = new ReqNHNUserInfo();
            reqNaverUserInfo.execute(mNaverLoginModule.getAccessToken(getApplicationContext()));
        }
    }

    class RefreshNHNToken extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                OAuthLogin mNaverLoginModule = OAuthLogin.getInstance();
                mNaverLoginModule.refreshAccessToken(getApplicationContext());
            } catch (Exception e) {
                Log.e("Error RefreshNHNToken", e.toString());
            }
            return true;
        }
    }

    class ReqNHNUserInfo extends AsyncTask<String, Void, String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            String token = strings[0]; //네이버 접근 토큰
            String header = "Bearer " + token;
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);

                int responseCode = con.getResponseCode();
                BufferedReader br;

                if (responseCode == 200) {
                    //정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    //error 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                result = response.toString();
                br.close();
                Log.d("ReqNHNUserInfo Response", result);
            } catch (Exception e) {
                Log.e("Error ReqNHNUserInfo", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(result);
                if (object.getString("resultcode").equals(NAVER_RESPONSE_CODE)) {
                    //정상 반환 시 반환코드는 "00"이다.
                    List<String> userInfo = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(object.getString("response"));

                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[USER_ID]));
                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[USER_NAME]));
                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[USER_EMAIL]));
                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[USER_PHONE]).replace("-",""));
                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[USER_BIRTHYEAR]).replace("-",""));
                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[USER_BIRTHDAY]).replace("-",""));
                    userInfo.add("naver");  //provider

                    ApplicationClass mGlobalHelper = new ApplicationClass();
                    mGlobalHelper.setGlobalUserLoginInfo(userInfo);

                    ((LoginActivity)LoginActivity.mContext).directToSecondActivity(true);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class DeleteTokenTask extends AsyncTask<Context, Void, Boolean> {
        Context context;

        public DeleteTokenTask(Context mContext) {
            this.context = mContext;
        }

        @Override
        protected Boolean doInBackground(Context... contexts) {
            return OAuthLogin.getInstance().logoutAndDeleteToken(contexts[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    }

}