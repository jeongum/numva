package com.egongil.numva_android_app.src.login;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.login.interfaces.SnsLoginActivityView;
import com.egongil.numva_android_app.src.login.interfaces.SnsLoginRetrofitInterface;
import com.egongil.numva_android_app.src.login.models.LinkSocialRequest;
import com.egongil.numva_android_app.src.login.models.LinkSocialResponse;
import com.egongil.numva_android_app.src.login.models.SocialRegisterRequest;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class SnSLoginService {
    private final SnsLoginActivityView mSnsLoginActivityView;

    public SnSLoginService(SnsLoginActivityView snsLoginExistEmailActivityView){
        mSnsLoginActivityView = snsLoginExistEmailActivityView;
    }
    public void linkSocial(LinkSocialRequest linkSocialRequest){
        final SnsLoginRetrofitInterface snsLoginRetrofitInterface = getRetrofit().create(SnsLoginRetrofitInterface.class);
        snsLoginRetrofitInterface.linkSocial(linkSocialRequest).enqueue(new Callback<LinkSocialResponse>() {
            @Override
            public void onResponse(Call<LinkSocialResponse> call, Response<LinkSocialResponse> response) {
                LinkSocialResponse linkSocialResponse=null;
                ErrorResponse errorResponse= null;
                if(response.body() != null){
                    linkSocialResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mSnsLoginActivityView.linkSocialSuccess(linkSocialResponse, errorResponse);

            }

            @Override
            public void onFailure(Call<LinkSocialResponse> call, Throwable t) {
                t.printStackTrace();
                mSnsLoginActivityView.linkSocialFailure();
            }
        });
    }

    public void socialRegister(SocialRegisterRequest socialRegisterRequest){
        final SnsLoginRetrofitInterface snsLoginRetrofitInterface = getRetrofit().create(SnsLoginRetrofitInterface.class);
        snsLoginRetrofitInterface.socialRegister(socialRegisterRequest).enqueue(new Callback<LinkSocialResponse>() {
            @Override
            public void onResponse(Call<LinkSocialResponse> call, Response<LinkSocialResponse> response) {
                LinkSocialResponse linkSocialResponse=null;
                ErrorResponse errorResponse= null;
                if(response.body() != null){
                    linkSocialResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mSnsLoginActivityView.socialRegisterSuccess(linkSocialResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<LinkSocialResponse> call, Throwable t) {
                t.printStackTrace();
                mSnsLoginActivityView.socialRegisterFailure();
            }
        });
    }
}
