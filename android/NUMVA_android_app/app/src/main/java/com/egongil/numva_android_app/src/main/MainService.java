package com.egongil.numva_android_app.src.main;

import android.util.Log;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.main.interfaces.MainActivityView;
import com.egongil.numva_android_app.src.main.interfaces.MainRetrofitInterface;
import com.egongil.numva_android_app.src.main.models.GetUserResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

public class MainService {
    private final MainActivityView mMainActivityView;

    public MainService(MainActivityView mMainActivityView){
        this.mMainActivityView = mMainActivityView;
    }
    void getUser(com.egongil.numva_android_app.src.config.Callback mCallback){
        final MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.getUser().enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                GetUserResponse getUserResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    getUserResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mMainActivityView.getUserSuccess(getUserResponse, errorResponse);

                mCallback.callback();
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                t.printStackTrace();
                mMainActivityView.getUserFailure();
                mCallback.callback();
            }
        });
    }
}