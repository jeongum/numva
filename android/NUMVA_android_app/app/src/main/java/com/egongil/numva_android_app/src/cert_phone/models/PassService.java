package com.egongil.numva_android_app.src.cert_phone.models;

import com.egongil.numva_android_app.src.cert_phone.interfaces.PassActivityContract;
import com.egongil.numva_android_app.src.config.models.request.PassRequest;
import com.egongil.numva_android_app.src.config.interfaces.RetrofitService;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.PassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public class PassService {
    private final PassActivityContract mPassActivityContract;

    public PassService(PassActivityContract mPassActivityContract){
        this.mPassActivityContract = mPassActivityContract;
    }

    public void postPass(PassRequest passRequest){
        final RetrofitService.PassRetrofitInterface passRetrofitInterface = getRetrofit().create(RetrofitService.PassRetrofitInterface.class);
        passRetrofitInterface.postPass(passRequest).enqueue(new Callback<PassResponse>(){
            @Override
            public void onResponse(Call<PassResponse> call, Response<PassResponse> response) {
                PassResponse passResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    passResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mPassActivityContract.postPassSuccess(passResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<PassResponse> call, Throwable t) {
                t.printStackTrace();
                mPassActivityContract.postPassFailure();
            }
        });
    }




}
