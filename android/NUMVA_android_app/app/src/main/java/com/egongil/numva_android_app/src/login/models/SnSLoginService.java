package com.egongil.numva_android_app.src.login.models;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.login.interfaces.SnsLoginActivityContract;
import com.egongil.numva_android_app.src.config.models.request.LinkSocialRequest;
import com.egongil.numva_android_app.src.config.models.response.LinkSocialResponse;
import com.egongil.numva_android_app.src.config.models.request.SocialRegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnSLoginService {
    private final SnsLoginActivityContract mSnsLoginActivityContract;

    public SnSLoginService(SnsLoginActivityContract snsLoginExistEmailActivityContract){
        mSnsLoginActivityContract = snsLoginExistEmailActivityContract;
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
                mSnsLoginActivityContract.linkSocialSuccess(linkSocialResponse, errorResponse);

            }

            @Override
            public void onFailure(Call<LinkSocialResponse> call, Throwable t) {
                t.printStackTrace();
                mSnsLoginActivityContract.linkSocialFailure();
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
                mSnsLoginActivityContract.socialRegisterSuccess(linkSocialResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<LinkSocialResponse> call, Throwable t) {
                t.printStackTrace();
                mSnsLoginActivityContract.socialRegisterFailure();
            }
        });
    }
}
