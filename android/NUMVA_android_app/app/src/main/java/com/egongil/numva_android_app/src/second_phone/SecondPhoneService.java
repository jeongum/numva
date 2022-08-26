package com.egongil.numva_android_app.src.second_phone;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityView;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneRegisterActivityView;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneRetrofitInterface;
import com.egongil.numva_android_app.src.config.models.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.SetSecondPhoneResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public class SecondPhoneService {

    private SecondPhoneActivityView mSecondPhoneActivityView;
    private SecondPhoneRegisterActivityView mSecondPhoneRegisterActivityView;


    public SecondPhoneService(SecondPhoneActivityView mSecondPhoneActivityView) {
        this.mSecondPhoneActivityView = mSecondPhoneActivityView;
    }

    public SecondPhoneService(SecondPhoneRegisterActivityView mSecondPhoneRegisterActivityView){
        this.mSecondPhoneRegisterActivityView = mSecondPhoneRegisterActivityView;
    }

    // Set Second Phone
    void setSecondPhone(SetSecondPhoneRequest setSecondPhoneRequest){
        final SecondPhoneRetrofitInterface secondPhoneRetrofitInterface = getRetrofit().create(SecondPhoneRetrofitInterface.class);
        secondPhoneRetrofitInterface.setSecondPhone(setSecondPhoneRequest).enqueue(new Callback<SetSecondPhoneResponse>(){
            // Set Second Phone 성공 시 함수 실행 정의
            @Override
            public void onResponse(Call<SetSecondPhoneResponse> call, Response<SetSecondPhoneResponse> response) {
                SetSecondPhoneResponse setSecondPhoneResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    setSecondPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneRegisterActivityView.setSecondPhoneSuccess(setSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<SetSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneRegisterActivityView.setSecondPhoneFailure();
            }
        });
    }

    // Get Second Phone
    void getSecondPhone(){
        final SecondPhoneRetrofitInterface secondPhoneRetrofitInterface = getRetrofit().create(SecondPhoneRetrofitInterface.class);
        secondPhoneRetrofitInterface.getSecondPhone().enqueue(new Callback<GetSecondPhoneResponse>(){
            @Override
            public void onResponse(Call<GetSecondPhoneResponse> call, Response<GetSecondPhoneResponse> response) {
                GetSecondPhoneResponse getSecondPhoneResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null) {
                    getSecondPhoneResponse = response.body();
                } else {
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneActivityView.getSecondPhoneSuccess(getSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneActivityView.getSecondPhoneFailure();
            }
        });
    }

    // Rep Second Phone
    void repSecondPhone(RepSecondPhoneRequest repSecondPhoneRequest){
        final SecondPhoneRetrofitInterface secondPhoneRetrofitInterface = getRetrofit().create(SecondPhoneRetrofitInterface.class);
        secondPhoneRetrofitInterface.repSecondPhone(repSecondPhoneRequest).enqueue(new Callback<RepSecondPhoneResponse>() {
            @Override
            public void onResponse(Call<RepSecondPhoneResponse> call, Response<RepSecondPhoneResponse> response) {
                RepSecondPhoneResponse repSecondPhoneResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    repSecondPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneActivityView.repSecondPhoneSuccess(repSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<RepSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneActivityView.repSecondPhoneFailure();
            }
        });
    }

    // Delete Second Phone
    void deleteSecondPhone(DeleteSecondPhoneRequest deleteSecondPhoneRequest){
        final SecondPhoneRetrofitInterface secondPhoneRetrofitInterface = getRetrofit().create(SecondPhoneRetrofitInterface.class);
        secondPhoneRetrofitInterface.deleteSecondPhone(deleteSecondPhoneRequest).enqueue(new Callback<DeleteSecondPhoneResponse>(){
            @Override
            public void onResponse(Call<DeleteSecondPhoneResponse> call, Response<DeleteSecondPhoneResponse> response) {
                DeleteSecondPhoneResponse deleteSecondPhoneResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    deleteSecondPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneActivityView.deleteSecondPhoneSuccess(deleteSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<DeleteSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneActivityView.deleteSecondPhoneFailure();
            }
        });
    }

    void postCertPhone(CertPhoneRequest certPhoneRequest){
        final SecondPhoneRetrofitInterface secondPhoneRetrofitInterface = getRetrofit().create(SecondPhoneRetrofitInterface.class);
        secondPhoneRetrofitInterface.postCertPhone(certPhoneRequest).enqueue(new Callback<CertPhoneResponse>() {
            @Override
            public void onResponse(Call<CertPhoneResponse> call, Response<CertPhoneResponse> response) {
                CertPhoneResponse certPhoneResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    certPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneRegisterActivityView.postCertPhoneSuccess(certPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<CertPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneRegisterActivityView.postCertPhoneFailure();
            }
        });
    }


}
