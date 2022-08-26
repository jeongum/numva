package com.egongil.numva_android_app.src.home.model;

import android.util.Log;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.home.interfaces.HomeFragmentContract;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class HomeService {
    private final HomeFragmentContract mHomeFragmentContract;

    public HomeService(HomeFragmentContract mHomeFragmentContract){
        this.mHomeFragmentContract = mHomeFragmentContract;
    }
    public void getSafetyInfo(){
        getRetrofitService().getSafetyInfo().enqueue(new retrofit2.Callback<RetrofitService.GetSafetyInfoResponse>() {
            @Override
            public void onResponse(Call<RetrofitService.GetSafetyInfoResponse> call, Response<RetrofitService.GetSafetyInfoResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                RetrofitService.GetSafetyInfoResponse getSafetyInfoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!= null) {
                    getSafetyInfoResponse = response.body();
                }
                else {
                    Converter<ResponseBody, ErrorResponse> errorConverter = getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mHomeFragmentContract.getSafetyInfoSuccess(getSafetyInfoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<RetrofitService.GetSafetyInfoResponse> call, Throwable t) {
                t.printStackTrace();
                mHomeFragmentContract.getSafetyInfoFailure();
            }
        });
    }
}
