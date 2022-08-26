package com.egongil.numva_android_app.src.cert_phone;

import com.egongil.numva_android_app.src.cert_phone.interfaces.PassActivityView;
import com.egongil.numva_android_app.src.cert_phone.interfaces.PassRetrofitInterface;
import com.egongil.numva_android_app.src.cert_phone.models.PassRequest;
import com.egongil.numva_android_app.src.cert_phone.models.PassResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public class PassService {
    private final PassActivityView mPassActivityView;

    public PassService(PassActivityView mPassActivityView){
        this.mPassActivityView = mPassActivityView;
    }

    void postPass(PassRequest passRequest){
        final PassRetrofitInterface passRetrofitInterface = getRetrofit().create(PassRetrofitInterface.class);
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
                mPassActivityView.postPassSuccess(passResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<PassResponse> call, Throwable t) {
                t.printStackTrace();
                mPassActivityView.postPassFailure();
            }
        });
    }




}
