package com.egongil.numva_android_app.src.main.viewmodels;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.GetUserResponse;
import com.egongil.numva_android_app.src.config.models.MutableListLiveData;
import com.egongil.numva_android_app.src.config.interfaces.RetrofitService;
import com.egongil.numva_android_app.src.config.models.SafetyInfo;
import com.egongil.numva_android_app.src.config.models.UserInfo;
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
    private MutableLiveData<UserInfo> mUserData;
    public MutableListLiveData<SafetyInfo> mSafetyInfo;

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
    public LiveData<UserInfo> getMutableData(){
        if(mUserData == null)
            mUserData = new MutableLiveData<>(new UserInfo());
        return mUserData;
    }
    public LiveData<UserInfo> getUserData(){
        if(mUserData == null)
            mUserData = new MutableLiveData<>(new UserInfo());
        return mUserData;
    }
    //TODO: getMutable -> getUserData로 변경 후, 다른 데이터들도 각각 getter 만들기

    public void setUserData(UserInfo data){
        getUserData();
        mUserData.setValue(data);
    }

    public MutableListLiveData<SafetyInfo> getSafetyInfoData(){
        if(mSafetyInfo == null){
            mSafetyInfo = new MutableListLiveData<>();
        }
        return mSafetyInfo;
    }

    public void setSafetyInfoData(ArrayList<SafetyInfo> safetyInfo) {
        if (mSafetyInfo == null) {
            getSafetyInfoData();
        }
        mSafetyInfo.setValue(safetyInfo);
    }

    public void getUser(){
        mRetrofitService.getUser().enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                GetUserResponse getUserResponse = null;
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
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                t.printStackTrace();
                mMainContract.getUserFailure();
            }
        });
    }



}
