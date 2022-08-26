package com.egongil.numva_android_app.src.main.viewmodels;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.MutableListLiveData;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.main.interfaces.MainContract;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> mLoginState;
    private MutableLiveData<RetrofitService.UserInfo> mUserData;
    public MutableListLiveData<RetrofitService.SafetyInfo> mSafetyInfo;

    private final MainContract mMainContract;
    private final RetrofitService mRetrofitService;

    public MainViewModel(MainContract mMainContract, RetrofitService retrofitService) {
        this.mMainContract = mMainContract;
        this.mRetrofitService = retrofitService;
    }

    public LiveData<Boolean> getLoginState(){
        if(mLoginState == null){
            mLoginState = new MutableLiveData<>();
        }
        return mLoginState;
    }

    public void setLoginState(boolean state){
        if(mUserData == null){
            getLoginState();
        }
        mLoginState.setValue(state);
    }

    //data binding 시 필요
    public LiveData<RetrofitService.UserInfo> getMutableData(){
        if(mUserData == null)
            mUserData = new MutableLiveData<>(new RetrofitService.UserInfo());
        return mUserData;
    }
    public LiveData<RetrofitService.UserInfo> getUserData(){
        if(mUserData == null)
            mUserData = new MutableLiveData<>(new RetrofitService.UserInfo());
        return mUserData;
    }
    //TODO: getMutable -> getUserData로 변경 후, 다른 데이터들도 각각 getter 만들기

    public void setUserData(RetrofitService.UserInfo data){
        getUserData();
        mUserData.setValue(data);
    }

    public MutableListLiveData<RetrofitService.SafetyInfo> getSafetyInfoData(){
        if(mSafetyInfo == null){
            mSafetyInfo = new MutableListLiveData<>();
        }
        return mSafetyInfo;
    }

    public void setSafetyInfoData(ArrayList<RetrofitService.SafetyInfo> safetyInfo) {
        if (mSafetyInfo == null) {
            getSafetyInfoData();
        }
        mSafetyInfo.setValue(safetyInfo);
    }

    public void getUser(){
        mRetrofitService.getUser().enqueue(new Callback<RetrofitService.GetUserResponse>() {
            @Override
            public void onResponse(Call<RetrofitService.GetUserResponse> call, Response<RetrofitService.GetUserResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                RetrofitService.GetUserResponse getUserResponse = null;
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
                mMainContract.getUserSuccess(getUserResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<RetrofitService.GetUserResponse> call, Throwable t) {
                t.printStackTrace();
                mMainContract.getUserFailure();
            }
        });
    }



}
