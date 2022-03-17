package com.egongil.numva_android_app.src.main.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<UserInfo> userData;
    //data binding 시 필요
    public LiveData<UserInfo> getMutableData(){
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
}
