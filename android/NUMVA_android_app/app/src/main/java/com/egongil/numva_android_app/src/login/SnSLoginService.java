package com.egongil.numva_android_app.src.login;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.login.interfaces.SnsLoginActivityView;
import com.egongil.numva_android_app.src.config.models.LinkSocialRequest;
import com.egongil.numva_android_app.src.config.models.LinkSocialResponse;
import com.egongil.numva_android_app.src.config.models.SocialRegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnSLoginService {
    private final SnsLoginActivityView mSnsLoginActivityView;

    public SnSLoginService(SnsLoginActivityView snsLoginExistEmailActivityView){
        mSnsLoginActivityView = snsLoginExistEmailActivityView;
    }
    public void linkSocial(LinkSocialRequest linkSocialRequest){
        getRetrofitService().linkSocial(linkSocialRequest).enqueue(new Callback<LinkSocialResponse>() {
            @Override
            public void onResponse(Call<LinkSocialResponse> call, Response<LinkSocialResponse> response) {
                LinkSocialResponse linkSocialResponse=null;
                ErrorResponse errorResponse= null;
                if(response.body() != null){
                    linkSocialResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
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
        getRetrofitService().socialRegister(socialRegisterRequest).enqueue(new Callback<LinkSocialResponse>() {
            @Override
            public void onResponse(Call<LinkSocialResponse> call, Response<LinkSocialResponse> response) {
                LinkSocialResponse linkSocialResponse=null;
                ErrorResponse errorResponse= null;
                if(response.body() != null){
                    linkSocialResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
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
