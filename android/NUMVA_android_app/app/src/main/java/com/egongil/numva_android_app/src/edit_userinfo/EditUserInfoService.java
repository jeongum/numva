package com.egongil.numva_android_app.src.edit_userinfo;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.EditUserInfoActivityView;
import com.egongil.numva_android_app.src.config.models.request.EditUserInfoRequest;
import com.egongil.numva_android_app.src.config.models.response.EditUserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class EditUserInfoService {

    private final EditUserInfoActivityView mEditUserInfoActivityView;

    public EditUserInfoService(EditUserInfoActivityView mEditUserInfoActivityView) {
        this.mEditUserInfoActivityView = mEditUserInfoActivityView;
    }

    void postEditUserInfo(EditUserInfoRequest editUserInfoRequest){
        getRetrofitService().postEditUserInfo(editUserInfoRequest).enqueue(new Callback<EditUserInfoResponse>(){
            @Override
            public void onResponse(Call<EditUserInfoResponse> call, Response<EditUserInfoResponse> response) {
                EditUserInfoResponse editUserInfoResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    editUserInfoResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mEditUserInfoActivityView.postEditUserInfoSuccess(editUserInfoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<EditUserInfoResponse> call, Throwable t) {
                t.printStackTrace();
                mEditUserInfoActivityView.postEditUserInfoFailure();
            }
        });

    }
}
