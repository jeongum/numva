package com.egongil.numva_android_app.src.main.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<GetUserResponse> userData;
    //data binding 시 필요
    public LiveData<GetUserResponse> getMutableData(){
        if(userData == null)
            userData = new MutableLiveData<>(new GetUserResponse());
        return userData;
    }
    public void setUserData(GetUserResponse data){
        userData.setValue(data);
    }

    public void setUserNickname(String nickname){
        GetUserResponse data = userData.getValue();
        data.setNickname(nickname);
        userData.setValue(data);
    }
}
