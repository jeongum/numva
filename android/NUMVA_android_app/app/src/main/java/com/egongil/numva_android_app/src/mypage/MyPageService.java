package com.egongil.numva_android_app.src.mypage;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageFragmentContract;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

public class MyPageService {
    private final MyPageFragmentContract mMyPageFragmentContract;

    public MyPageService(MyPageFragmentContract mMyPageFragmentContract) {
        this.mMyPageFragmentContract = mMyPageFragmentContract;
    }

    void getLogout(){
        getRetrofitService().getLogout().enqueue(new Callback<RetrofitService.LogoutResponse>() {
            @Override
            public void onResponse(Call<RetrofitService.LogoutResponse> call, Response<RetrofitService.LogoutResponse> response) {
                RetrofitService.LogoutResponse logoutResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    logoutResponse = response.body();
                }else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mMyPageFragmentContract.getLogoutSuccess(logoutResponse, errorResponse);

            }

            @Override
            public void onFailure(Call<RetrofitService.LogoutResponse> call, Throwable t) {
                t.printStackTrace();
                mMyPageFragmentContract.getLogoutFailure();
            }
        });
    }
}
