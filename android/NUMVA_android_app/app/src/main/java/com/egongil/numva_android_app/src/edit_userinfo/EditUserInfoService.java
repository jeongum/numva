package com.egongil.numva_android_app.src.edit_userinfo;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.EditUserInfoActivityView;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.EditUserInfoRetrofitInterface;
import com.egongil.numva_android_app.src.edit_userinfo.models.EditUserInfoRequest;
import com.egongil.numva_android_app.src.edit_userinfo.models.EditUserInfoResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

public class EditUserInfoService {

    private final EditUserInfoActivityView mEditUserInfoActivityView;

    public EditUserInfoService(EditUserInfoActivityView mEditUserInfoActivityView) {
        this.mEditUserInfoActivityView = mEditUserInfoActivityView;
    }

    void postEditUserInfo(EditUserInfoRequest editUserInfoRequest){
        final EditUserInfoRetrofitInterface editUserInfoRetrofitInterface = getRetrofit().create(EditUserInfoRetrofitInterface.class);
        editUserInfoRetrofitInterface.postEditUserInfo(editUserInfoRequest).enqueue(new Callback<EditUserInfoResponse>(){
            @Override
            public void onResponse(Call<EditUserInfoResponse> call, Response<EditUserInfoResponse> response) {
                EditUserInfoResponse editUserInfoResponse=null;
                ErrorResponse errorResponse=null;
                if(response.body()!=null){
                    editUserInfoResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse=errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
