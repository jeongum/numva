package com.egongil.numva_android_app.src.second_phone.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityContract;

public class SecondPhoneViewModelFactory implements ViewModelProvider.Factory{
    SecondPhoneActivityContract mSecondPhoneActivityContract;

    public SecondPhoneViewModelFactory(SecondPhoneActivityContract mSecondPhoneActivityContract) {
        this.mSecondPhoneActivityContract = mSecondPhoneActivityContract;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SecondPhoneViewModel(mSecondPhoneActivityContract);
    }
}
