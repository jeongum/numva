package com.egongil.numva_android_app.src.main.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.egongil.numva_android_app.src.config.interfaces.RetrofitService;
import com.egongil.numva_android_app.src.main.interfaces.MainContract;

public class MainViewModelFactory implements ViewModelProvider.Factory{
    private MainContract mMainContract;
    private RetrofitService mRetrofitService;

    public MainViewModelFactory(MainContract mMainContract, RetrofitService mRetrofitService) {
        this.mMainContract = mMainContract;
        this.mRetrofitService = mRetrofitService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mMainContract, mRetrofitService);
    }
}
