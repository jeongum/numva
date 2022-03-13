package com.egongil.numva_android_app.src.config;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

public class XAccessTokenInterceptor implements Interceptor {
    @NotNull
    @Override
    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
        final Request newRequest;
        final String jwtToken = sSharedPreferences.getString(X_ACCESS_TOKEN, null);
        Log.e("token","jwtToken :"+ jwtToken);

        //Authorization 헤더에 토큰 추가
        if(jwtToken != null && !jwtToken.equals("")){
            newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer "+jwtToken).build();
            chain.getClass();
        }else newRequest = chain.request();

        return chain.proceed(newRequest);
        //TODO: 토큰 만료 여부 체크

//        if(jwtToken != null){
//            builder.addHeader(X_ACCESS_TOKEN, jwtToken);
//        }
//        return chain.proceed(builder.build());
    }
}
