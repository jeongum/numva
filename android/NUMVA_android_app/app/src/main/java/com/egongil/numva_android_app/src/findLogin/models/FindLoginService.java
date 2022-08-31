package com.egongil.numva_android_app.src.findLogin.models;

import com.egongil.numva_android_app.src.config.models.request.CertPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.interfaces.FindIdActivityContract;
import com.egongil.numva_android_app.src.findLogin.interfaces.FindPwActivityContract;
import com.egongil.numva_android_app.src.findLogin.interfaces.ResetPwActivityContract;
import com.egongil.numva_android_app.src.config.models.request.FindIdRequest;
import com.egongil.numva_android_app.src.config.models.response.FindIdResponse;
import com.egongil.numva_android_app.src.config.models.request.FindPwRequest;
import com.egongil.numva_android_app.src.config.models.response.FindPwResponse;
import com.egongil.numva_android_app.src.config.models.request.ResetPwRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class FindLoginService {
    private FindIdActivityContract mFindIdActivityContract;
    private FindPwActivityContract mFindPwActivityContract;
    private ResetPwActivityContract mResetPwActivityContract;
    private FindIdActivityContract mCertPhoneActivityContract;

    public FindLoginService(FindIdActivityContract mFindLoginActivityContract) {
        this.mFindIdActivityContract = mFindLoginActivityContract;
    }

    public FindLoginService(FindPwActivityContract mFindPwActivityContract){
        this.mFindPwActivityContract = mFindPwActivityContract;
    }

    public FindLoginService(ResetPwActivityContract mResetPwActivityContract){
        this.mResetPwActivityContract = mResetPwActivityContract;
    }

    public void postFindId(FindIdRequest findIdRequest){
        getRetrofitService().postFindId(findIdRequest).enqueue(new Callback<FindIdResponse>(){
            @Override
            public void onResponse(Call<FindIdResponse> call, Response<FindIdResponse> response) {
                FindIdResponse findIdResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    findIdResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mFindIdActivityContract.postFindIdSuccess(findIdResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FindIdResponse> call, Throwable t){
                t.printStackTrace();
                mFindIdActivityContract.postFindIdFailure();
            }
        });
    }

    public void postFindPw(FindPwRequest findPwRequest){
        getRetrofitService().postFindPw(findPwRequest).enqueue(new Callback<FindPwResponse>() {
            @Override
            public void onResponse(Call<FindPwResponse> call, Response<FindPwResponse> response) {
                FindPwResponse findPwResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    findPwResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
                }
                mFindPwActivityContract.postFindPwSuccess(findPwResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FindPwResponse> call, Throwable t) {
                t.printStackTrace();
                mFindPwActivityContract.postFindPwFailure();
            }
        });
    }

    public void postResetPw(ResetPwRequest resetPwRequest){
        getRetrofitService().postResetPw(resetPwRequest).enqueue(new Callback<FindPwResponse>(){
            @Override
            public void onResponse(Call<FindPwResponse> call, Response<FindPwResponse> response) {
                FindPwResponse findPwResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    findPwResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mResetPwActivityContract.postResetPwSuccess(findPwResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FindPwResponse> call, Throwable t) {
                t.printStackTrace();
                mResetPwActivityContract.postResetPwFailure();
            }
        });
    }

    public void postCertPhone(CertPhoneRequest certPhoneRequest){
        getRetrofitService().postCertPhone(certPhoneRequest).enqueue(new Callback<CertPhoneResponse>(){
            @Override
            public void onResponse(Call<CertPhoneResponse> call, Response<CertPhoneResponse> response) {
                CertPhoneResponse certPhoneResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    certPhoneResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
                }
                if(call== mFindIdActivityContract){
                    mFindIdActivityContract.postCertPhoneSuccess(certPhoneResponse, errorResponse);
                }
                if(call== mFindPwActivityContract){
                    mFindPwActivityContract.postCertPhoneSuccess(certPhoneResponse, errorResponse);
                }

            }
            @Override
            public void onFailure(Call<CertPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                if (call == mFindIdActivityContract) {
                    mFindIdActivityContract.postCertPhoneFailure();
                }
                if(call== mFindPwActivityContract){
                    mFindPwActivityContract.postCertPhoneFailure();
                }

            }
        });
    }




}
