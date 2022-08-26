package com.egongil.numva_android_app.src.findLogin;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.interfaces.FindIdActivityView;
import com.egongil.numva_android_app.src.findLogin.interfaces.FindPwActivityView;
import com.egongil.numva_android_app.src.findLogin.interfaces.ResetPwActivityView;
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
    private FindIdActivityView mFindIdActivityView;
    private FindPwActivityView mFindPwActivityView;
    private ResetPwActivityView mResetPwActivityView;
    private FindIdActivityView mCertPhoneActivityView;

    public FindLoginService(FindIdActivityView mFindLoginActivityView) {
        this.mFindIdActivityView = mFindLoginActivityView;
    }

    public FindLoginService(FindPwActivityView mFindPwActivityView){
        this.mFindPwActivityView = mFindPwActivityView;
    }

    public FindLoginService(ResetPwActivityView mResetPwActivityView){
        this.mResetPwActivityView = mResetPwActivityView;
    }

    void postFindId(FindIdRequest findIdRequest){
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
                mFindIdActivityView.postFindIdSuccess(findIdResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FindIdResponse> call, Throwable t){
                t.printStackTrace();
                mFindIdActivityView.postFindIdFailure();
            }
        });
    }

    void postFindPw(FindPwRequest findPwRequest){
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
                mFindPwActivityView.postFindPwSuccess(findPwResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FindPwResponse> call, Throwable t) {
                t.printStackTrace();
                mFindPwActivityView.postFindPwFailure();
            }
        });
    }

    void postResetPw(ResetPwRequest resetPwRequest){
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
                mResetPwActivityView.postResetPwSuccess(findPwResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FindPwResponse> call, Throwable t) {
                t.printStackTrace();
                mResetPwActivityView.postResetPwFailure();
            }
        });
    }

    void postCertPhone(CertPhoneRequest certPhoneRequest){
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
                if(call==mFindIdActivityView){
                    mFindIdActivityView.postCertPhoneSuccess(certPhoneResponse, errorResponse);
                }
                if(call==mFindPwActivityView){
                    mFindPwActivityView.postCertPhoneSuccess(certPhoneResponse, errorResponse);
                }

            }
            @Override
            public void onFailure(Call<CertPhoneResponse> call, Throwable t) {
                t.printStackTrace();
                if (call == mFindIdActivityView) {
                    mFindIdActivityView.postCertPhoneFailure();
                }
                if(call==mFindPwActivityView){
                    mFindPwActivityView.postCertPhoneFailure();
                }

            }
        });
    }




}
