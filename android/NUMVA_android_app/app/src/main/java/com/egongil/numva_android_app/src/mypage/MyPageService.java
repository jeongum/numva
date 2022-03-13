package com.egongil.numva_android_app.src.mypage;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageFragmentView;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageRetrofitInterface;
import com.egongil.numva_android_app.src.mypage.models.LogoutResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

public class MyPageService {
    private final MyPageFragmentView mMyPageFragmentView;

    public MyPageService(MyPageFragmentView mMyPageFragmentView) {
        this.mMyPageFragmentView = mMyPageFragmentView;
    }

    void getLogout(){
        final MyPageRetrofitInterface myPageRetrofitInterface = getRetrofit().create(MyPageRetrofitInterface.class);
        myPageRetrofitInterface.getLogout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                LogoutResponse logoutResponse = null;
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
                mMyPageFragmentView.getLogoutSuccess(logoutResponse, errorResponse);

            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                t.printStackTrace();
                mMyPageFragmentView.getLogoutFailure();
            }
        });
    }
}
