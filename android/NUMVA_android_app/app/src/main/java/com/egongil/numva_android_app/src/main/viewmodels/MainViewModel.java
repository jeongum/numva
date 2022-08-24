package com.egongil.numva_android_app.src.main.viewmodels;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.MutableListLiveData;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.egongil.numva_android_app.src.main.interfaces.MainActivityView;
import com.egongil.numva_android_app.src.main.models.MainService;
import com.egongil.numva_android_app.src.main.models.MainService.UserInfo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> mLoginState; //TODO: 로그인 상태 저장
    private MutableLiveData<MainService.UserInfo> mUserData;
    public MutableListLiveData<SafetyInfo> mSafetyInfo;

    private final MainActivityView mMainActivityView;
    private final RetrofitService mRetrofitService;

    public MainViewModel(MainActivityView mMainActivityView, RetrofitService retrofitService) {
        this.mMainActivityView = mMainActivityView;
        this.mRetrofitService = retrofitService;
    }

    //data binding 시 필요
    public LiveData<MainService.UserInfo> getMutableData(){
        if(mUserData == null)
            mUserData = new MutableLiveData<>(new UserInfo());
        return mUserData;
    }
    public LiveData<MainService.UserInfo> getUserData(){
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
