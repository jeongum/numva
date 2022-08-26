package com.egongil.numva_android_app.src.signup;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.signup.interfaces.SignupActivityView;
import com.egongil.numva_android_app.src.signup.interfaces.SignupRetrofitInterface;
import com.egongil.numva_android_app.src.config.models.SignupRequest;
import com.egongil.numva_android_app.src.config.models.SignupResponse;
import com.egongil.numva_android_app.src.config.models.ValidEmailRequest;
import com.egongil.numva_android_app.src.config.models.ValidEmailResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public class SignupService {

    private final SignupActivityView mSignupActivityView;


    public SignupService(SignupActivityView mSignupActivityView) {
        this.mSignupActivityView = mSignupActivityView;
    }

    //postSignup : 실직적으로 post를 실행하는 함수
    void postSignup(SignupRequest signupRequest){
        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);
        signupRetrofitInterface.postSignup(signupRequest).enqueue(new Callback<SignupResponse>() {
            //성공 시 함수 실행 정의
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse signupResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){  //성공해서 body로 파싱할 때
                    signupResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mSignupActivityView.postSignupSuccess(signupResponse, errorResponse);
            }

            //실패 시 함수 실행 정의
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                t.printStackTrace();
                mSignupActivityView.postSignupFailure();
            }
        });
    }

    void postValidEmail(ValidEmailRequest validEmailRequest){
        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);
        signupRetrofitInterface.postValidEmail(validEmailRequest).enqueue(new Callback<ValidEmailResponse>(){

            @Override
            public void onResponse(Call<ValidEmailResponse> call, Response<ValidEmailResponse> response) {
                ValidEmailResponse validEmailResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    validEmailResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mSignupActivityView.postValidEmailSuccess(validEmailResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<ValidEmailResponse> call, Throwable t) {
                t.printStackTrace();
                mSignupActivityView.postValidEmailFailure();
            }
        });
    }
}
