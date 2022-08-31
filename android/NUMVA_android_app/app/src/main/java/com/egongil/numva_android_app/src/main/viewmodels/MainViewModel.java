package com.egongil.numva_android_app.src.main.viewmodels;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

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
    //observe 등록 위해 public
    public MutableListLiveData<SafetyInfo> mSafetyInfo;

    private final MainContract mMainContract;

    public MainViewModel(MainContract mMainContract) {
        this.mMainContract = mMainContract;
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

    public LiveData<UserInfo> getUserData(){
        if(mUserData == null)
            mUserData = new MutableLiveData<>(new UserInfo());
        return mUserData;
    }

    public void setUserData(UserInfo data){
        if(mUserData == null)
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

    public void setParkingMemo(int pos, String newMemo){
        ArrayList<SafetyInfo> newList = mSafetyInfo.getValue();
        newList.set(pos, newList.get(pos).setMemo(newMemo));
        mSafetyInfo.setValue(newList);
    }

    //api 호출 함수
    public void getUser(){
        getRetrofitService().getUser().enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));

                GetUserResponse getUserResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    getUserResponse = response.body();
                }
                else{
                    errorResponse = convertErrorResponse(response);
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
