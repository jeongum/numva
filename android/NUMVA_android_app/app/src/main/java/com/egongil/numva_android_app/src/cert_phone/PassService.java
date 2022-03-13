package com.egongil.numva_android_app.src.cert_phone;

import com.egongil.numva_android_app.src.cert_phone.interfaces.PassActivityView;
import com.egongil.numva_android_app.src.cert_phone.interfaces.PassRetrofitInterface;
import com.egongil.numva_android_app.src.cert_phone.models.PassRequest;
import com.egongil.numva_android_app.src.cert_phone.models.PassResponse;
import com.egongil.numva_android_app.src.config.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

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
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
