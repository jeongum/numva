package com.egongil.numva_android_app.src.edit_userinfo;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.DeleteAccountActivityView;
import com.egongil.numva_android_app.src.config.models.response.DeleteAccountResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class DeleteAccountService {

    private final DeleteAccountActivityView mDeleteAccountActivityView;

    public DeleteAccountService(DeleteAccountActivityView mDeleteAccountActivityView) {
        this.mDeleteAccountActivityView = mDeleteAccountActivityView;
    }

    void deleteAccount(){
        getRetrofitService().deleteAccount().enqueue(new Callback<DeleteAccountResponse>(){
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {
                DeleteAccountResponse deleteAccountResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    deleteAccountResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
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
