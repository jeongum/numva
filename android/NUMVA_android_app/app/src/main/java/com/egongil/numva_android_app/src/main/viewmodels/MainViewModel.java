package com.egongil.numva_android_app.src.main.viewmodels;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.main.interfaces.MainActivityView;
import com.egongil.numva_android_app.src.main.interfaces.MainRetrofitInterface;
import com.egongil.numva_android_app.src.main.models.MainService;
import com.egongil.numva_android_app.src.main.models.MainService.UserInfo;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainViewModel extends ViewModel {
    private MutableLiveData<MainService.UserInfo> userData;
    private final MainActivityView mMainActivityView;
    private final RetrofitService mRetrofitService;

    public MainViewModel(MainActivityView mMainActivityView, RetrofitService retrofitService) {
        this.mMainActivityView = mMainActivityView;
        this.mRetrofitService = retrofitService;
    }

    //data binding 시 필요
    public LiveData<MainService.UserInfo> getMutableData(){
        if(userData == null)
            userData = new MutableLiveData<>(new UserInfo());
        return userData;
    }

    public void setUserData(UserInfo data){
        getMutableData();
        userData.setValue(data);
    }

    public void setUserNickname(String nickname){
        UserInfo data = userData.getValue();
        data.setNickname(nickname);
        userData.setValue(data);
    }

    public void getUser(){
        mRetrofitService.getUser().enqueue(new Callback<MainService.GetUserResponse>() {
            @Override
            public void onResponse(Call<MainService.GetUserResponse> call, Response<MainService.GetUserResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                MainService.GetUserResponse getUserResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    getUserResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mMainActivityView.getUserSuccess(getUserResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<MainService.GetUserResponse> call, Throwable t) {
                t.printStackTrace();
                mMainActivityView.getUserFailure();
            }
        });
    }
}
