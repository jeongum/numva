package com.egongil.numva_android_app.src.home;

import android.util.Log;

import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.home.interfaces.HomeFragmentView;
import com.egongil.numva_android_app.src.home.interfaces.HomeRetrofitInterface;
import com.egongil.numva_android_app.src.home.models.GetSafetyInfoResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public class HomeService {
    private final HomeFragmentView mHomeFragmentView;

    public HomeService(HomeFragmentView mHomeFragmentView){
        this.mHomeFragmentView = mHomeFragmentView;
    }
    void getSafetyInfo(Callback mCallback){
        final HomeRetrofitInterface homeRetrofitInterface = getRetrofit().create(HomeRetrofitInterface.class);
        homeRetrofitInterface.getSafetyInfo().enqueue(new retrofit2.Callback<GetSafetyInfoResponse>() {
            @Override
            public void onResponse(Call<GetSafetyInfoResponse> call, Response<GetSafetyInfoResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                GetSafetyInfoResponse getSafetyInfoResponse = null;
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
                mHomeFragmentView.getSafetyInfoSuccess(getSafetyInfoResponse, errorResponse);
                mCallback.callback();
            }

            @Override
            public void onFailure(Call<GetSafetyInfoResponse> call, Throwable t) {
                t.printStackTrace();
                mHomeFragmentView.getSafetyInfoFailure();
                mCallback.callback();
            }
        });
    }
}
