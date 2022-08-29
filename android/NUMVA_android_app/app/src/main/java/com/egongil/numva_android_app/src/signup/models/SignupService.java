package com.egongil.numva_android_app.src.signup.models;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.signup.interfaces.SignupActivityContract;
import com.egongil.numva_android_app.src.config.models.request.SignupRequest;
import com.egongil.numva_android_app.src.config.models.response.SignupResponse;
import com.egongil.numva_android_app.src.config.models.request.ValidEmailRequest;
import com.egongil.numva_android_app.src.config.models.response.ValidEmailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class SignupService {
    private final SignupActivityContract mSignupActivityContract;

    public SignupService(SignupActivityContract mSignupActivityContract) {
        this.mSignupActivityContract = mSignupActivityContract;
    }

    //postSignup : 실질적으로 post를 실행하는 함수
    public void postSignup(SignupRequest signupRequest){
        getRetrofitService().postSignup(signupRequest).enqueue(new Callback<SignupResponse>() {
            //성공 시 함수 실행 정의
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse signupResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){  //성공해서 body로 파싱할 때
                    signupResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
                }
                mSignupActivityContract.postSignupSuccess(signupResponse, errorResponse);
            }

            //실패 시 함수 실행 정의
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                t.printStackTrace();
                mSignupActivityContract.postSignupFailure();
            }
        });
    }

    public void postValidEmail(ValidEmailRequest validEmailRequest){
        getRetrofitService().postValidEmail(validEmailRequest).enqueue(new Callback<ValidEmailResponse>(){

            @Override
            public void onResponse(Call<ValidEmailResponse> call, Response<ValidEmailResponse> response) {
                ValidEmailResponse validEmailResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    validEmailResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
                }
                mSignupActivityContract.postValidEmailSuccess(validEmailResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<ValidEmailResponse> call, Throwable t) {
                t.printStackTrace();
                mSignupActivityContract.postValidEmailFailure();
            }
        });
    }
}
