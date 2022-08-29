package com.egongil.numva_android_app.src.login.models;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.login.interfaces.LoginActivityContract;
import com.egongil.numva_android_app.src.config.models.request.LoginRequest;
import com.egongil.numva_android_app.src.config.models.response.LoginResponse;
import com.egongil.numva_android_app.src.config.models.request.SocialLoginRequest;
import com.egongil.numva_android_app.src.config.models.response.SocialLoginResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.config.models.request.ValidEmailRequest;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class LoginService {
    private final LoginActivityContract mLoginActivityContract;


    public LoginService(LoginActivityContract mLoginActivityContract) {
        this.mLoginActivityContract = mLoginActivityContract;
    }

    public void postLogin(LoginRequest loginRequest){
        getRetrofitService().postLogin(loginRequest).enqueue(new Callback<LoginResponse>(){
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    loginResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mLoginActivityContract.postLoginSuccess(loginResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                mLoginActivityContract.postLoginFailure();
            }
        });
    }

    public void isValidEmail(ValidEmailRequest validEmailRequest){
        getRetrofitService().isValidEmail(validEmailRequest).enqueue(new Callback<SocialValidEmailResponse>(){

            @Override
            public void onResponse(Call<SocialValidEmailResponse> call, Response<SocialValidEmailResponse> response) {
                SocialValidEmailResponse validEmailResponse = null;
                SocialValidEmailErrorResponse errorResponse = null;
                if(response.body()!=null){
                    validEmailResponse = response.body();
                }
                else{
                    Converter<ResponseBody, SocialValidEmailErrorResponse> errorConverter = getRetrofit().responseBodyConverter(SocialValidEmailErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mLoginActivityContract.isValidEmailSuccess(validEmailResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<SocialValidEmailResponse> call, Throwable t) {
                t.printStackTrace();
                mLoginActivityContract.isValidEmailFailure();
            }
        });
    }

    public void socialLogin(SocialLoginRequest socialLoginRequest){
        getRetrofitService().socialLogin(socialLoginRequest).enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                SocialLoginResponse socialLoginResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                  socialLoginResponse = response.body();
                }else{
                   errorResponse = convertErrorResponse(response);
                }
                mLoginActivityContract.socialLoginSucceess(socialLoginResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                t.printStackTrace();
                mLoginActivityContract.socialLoginFailure();
            }
        });
    }
}
