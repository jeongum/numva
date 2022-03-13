package com.egongil.numva_android_app.src.edit_userinfo.interfaces;

import com.egongil.numva_android_app.src.edit_userinfo.models.DeleteAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;

public interface DeleteAccountRetrofitInterface {
    @DELETE("auth/delete")
    Call<DeleteAccountResponse> deleteAccount();
}
