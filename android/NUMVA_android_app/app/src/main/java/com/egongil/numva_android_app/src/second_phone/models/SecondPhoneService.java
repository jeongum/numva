package com.egongil.numva_android_app.src.second_phone.models;

import com.egongil.numva_android_app.src.config.models.request.CertPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityContract;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneRegisterActivityContract;
import com.egongil.numva_android_app.src.config.models.request.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.SetSecondPhoneResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class SecondPhoneService {

    private SecondPhoneActivityContract mSecondPhoneActivityContract;
    private SecondPhoneRegisterActivityContract mSecondPhoneRegisterActivityContract;


    public SecondPhoneService(SecondPhoneActivityContract mSecondPhoneActivityContract) {
        this.mSecondPhoneActivityContract = mSecondPhoneActivityContract;
    }

    public SecondPhoneService(SecondPhoneRegisterActivityContract mSecondPhoneRegisterActivityContract){
        this.mSecondPhoneRegisterActivityContract = mSecondPhoneRegisterActivityContract;
    }

    // Set Second Phone
    public void setSecondPhone(SetSecondPhoneRequest setSecondPhoneRequest){
        getRetrofitService().setSecondPhone(setSecondPhoneRequest).enqueue(new Callback<SetSecondPhoneResponse>(){
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
                mSecondPhoneRegisterActivityContract.setSecondPhoneSuccess(setSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<SetSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneRegisterActivityContract.setSecondPhoneFailure();
            }
        });
    }

    // Get Second Phone
    public void getSecondPhone(){
        getRetrofitService().getSecondPhone().enqueue(new Callback<GetSecondPhoneResponse>(){
            @Override
            public void onResponse(Call<GetSecondPhoneResponse> call, Response<GetSecondPhoneResponse> response) {
                GetSecondPhoneResponse getSecondPhoneResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null) {
                    getSecondPhoneResponse = response.body();
                } else {
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneActivityContract.getSecondPhoneSuccess(getSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneActivityContract.getSecondPhoneFailure();
            }
        });
    }

    // Rep Second Phone
    public void repSecondPhone(RepSecondPhoneRequest repSecondPhoneRequest){
        getRetrofitService().repSecondPhone(repSecondPhoneRequest).enqueue(new Callback<RepSecondPhoneResponse>() {
            @Override
            public void onResponse(Call<RepSecondPhoneResponse> call, Response<RepSecondPhoneResponse> response) {
                RepSecondPhoneResponse repSecondPhoneResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    repSecondPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneActivityContract.repSecondPhoneSuccess(repSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<RepSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneActivityContract.repSecondPhoneFailure();
            }
        });
    }

    // Delete Second Phone
    public void deleteSecondPhone(DeleteSecondPhoneRequest deleteSecondPhoneRequest){
        getRetrofitService().deleteSecondPhone(deleteSecondPhoneRequest).enqueue(new Callback<DeleteSecondPhoneResponse>(){
            @Override
            public void onResponse(Call<DeleteSecondPhoneResponse> call, Response<DeleteSecondPhoneResponse> response) {
                DeleteSecondPhoneResponse deleteSecondPhoneResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    deleteSecondPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneActivityContract.deleteSecondPhoneSuccess(deleteSecondPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<DeleteSecondPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneActivityContract.deleteSecondPhoneFailure();
            }
        });
    }

    public void postCertPhone(CertPhoneRequest certPhoneRequest){
        getRetrofitService().postCertPhone(certPhoneRequest).enqueue(new Callback<CertPhoneResponse>() {
            @Override
            public void onResponse(Call<CertPhoneResponse> call, Response<CertPhoneResponse> response) {
                CertPhoneResponse certPhoneResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    certPhoneResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mSecondPhoneRegisterActivityContract.postCertPhoneSuccess(certPhoneResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<CertPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                mSecondPhoneRegisterActivityContract.postCertPhoneFailure();
            }
        });
    }


}
