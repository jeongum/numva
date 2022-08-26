package com.egongil.numva_android_app.src.qr_management;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import android.util.Log;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.qr_management.interfaces.QrManagementActivityView;
import com.egongil.numva_android_app.src.config.models.DeleteQrRequest;
import com.egongil.numva_android_app.src.config.models.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.SetQrNameRequest;
import com.egongil.numva_android_app.src.config.models.SetQrNameResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class QrManagementService {
    private final QrManagementActivityView qrManagementActivityView;

    public QrManagementService(QrManagementActivityView qrManagementActivityView){
        this.qrManagementActivityView = qrManagementActivityView;
    }
    void getSafetyInfo(){
        getRetrofitService().getSafetyInfo().enqueue(new retrofit2.Callback<GetSafetyInfoResponse>() {
            @Override
            public void onResponse(Call<GetSafetyInfoResponse> call, Response<GetSafetyInfoResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                GetSafetyInfoResponse getSafetyInfoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!= null) {
                    getSafetyInfoResponse = response.body();
                }
                else {
                    errorResponse = convertErrorResponse(response);
                }
                qrManagementActivityView.getSafetyInfoSuccess(getSafetyInfoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetSafetyInfoResponse> call, Throwable t) {
                t.printStackTrace();
                qrManagementActivityView.getSafetyInfoFailure();
            }
        });
    }

    void setQrName(SetQrNameRequest setQrNameRequest){
        getRetrofitService().setQrName(setQrNameRequest).enqueue(new retrofit2.Callback<SetQrNameResponse>(){
            @Override
            public void onResponse(Call<SetQrNameResponse> call, Response<SetQrNameResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                SetQrNameResponse setQrNameResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!= null) {
                    setQrNameResponse = response.body();
                }
                else {
                    errorResponse = convertErrorResponse(response);
                }
                qrManagementActivityView.setQrNameSuccess(setQrNameResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<SetQrNameResponse> call, Throwable t) {
                t.printStackTrace();
                qrManagementActivityView.setQrNameFailure();
            }
        });
    }

    void registerQr(RegisterQrRequest registerQrRequest){
        getRetrofitService().registerQr(registerQrRequest).enqueue(new retrofit2.Callback<RegisterQrResponse>(){
            @Override
            public void onResponse(Call<RegisterQrResponse> call, Response<RegisterQrResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));
                RegisterQrResponse registerQrResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    registerQrResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
                }
                qrManagementActivityView.registerQrSuccess(registerQrResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<RegisterQrResponse> call, Throwable t) {
                t.printStackTrace();
                qrManagementActivityView.registerQrFailure();
            }
        });
    }

    void deleteQr(DeleteQrRequest deleteQrRequest){
        getRetrofitService().deleteQr(deleteQrRequest).enqueue(new retrofit2.Callback<DeleteQrResponse>(){
            @Override
            public void onResponse(Call<DeleteQrResponse> call, Response<DeleteQrResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));
                DeleteQrResponse deleteQrResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    deleteQrResponse = response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                qrManagementActivityView.deleteQrSuccess(deleteQrResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<DeleteQrResponse> call, Throwable t) {
                t.printStackTrace();
                qrManagementActivityView.deleteQrFailure();
            }
        });
    }
}
