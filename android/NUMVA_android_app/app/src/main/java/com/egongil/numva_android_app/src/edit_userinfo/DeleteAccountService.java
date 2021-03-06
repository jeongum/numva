package com.egongil.numva_android_app.src.edit_userinfo;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.DeleteAccountActivityView;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.DeleteAccountRetrofitInterface;
import com.egongil.numva_android_app.src.edit_userinfo.models.DeleteAccountResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

public class DeleteAccountService {

    private final DeleteAccountActivityView mDeleteAccountActivityView;

    public DeleteAccountService(DeleteAccountActivityView mDeleteAccountActivityView) {
        this.mDeleteAccountActivityView = mDeleteAccountActivityView;
    }

    void deleteAccount(){
        final DeleteAccountRetrofitInterface deleteAccountRetrofitInterface = getRetrofit().create(DeleteAccountRetrofitInterface.class);
        deleteAccountRetrofitInterface.deleteAccount().enqueue(new Callback<DeleteAccountResponse>(){
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {
                DeleteAccountResponse deleteAccountResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    deleteAccountResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mDeleteAccountActivityView.deleteAccountSuccess(deleteAccountResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                t.printStackTrace();
                mDeleteAccountActivityView.deleteAccountFailure();
            }
        });
    }
}
