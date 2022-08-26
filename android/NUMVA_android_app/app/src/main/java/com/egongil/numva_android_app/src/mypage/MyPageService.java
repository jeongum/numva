package com.egongil.numva_android_app.src.mypage;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.LogoutResponse;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageFragmentContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class MyPageService {
    private final MyPageFragmentContract mMyPageFragmentContract;

    public MyPageService(MyPageFragmentContract mMyPageFragmentContract) {
        this.mMyPageFragmentContract = mMyPageFragmentContract;
    }

    void getLogout(){
        getRetrofitService().getLogout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                LogoutResponse logoutResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    logoutResponse = response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                mMyPageFragmentContract.getLogoutSuccess(logoutResponse, errorResponse);

            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                t.printStackTrace();
                mMyPageFragmentContract.getLogoutFailure();
            }
        });
    }
}
