package com.egongil.numva_android_app.src.main.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.main.interfaces.MainActivityView;

public class MainViewModelFactory implements ViewModelProvider.Factory{
    private MainActivityView mMainActivityView;
    private RetrofitService mRetrofitService;

    public MainViewModelFactory(MainActivityView mMainActivityView, RetrofitService mRetrofitService) {
        this.mMainActivityView = mMainActivityView;
        this.mRetrofitService = mRetrofitService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mMainActivityView, mRetrofitService);
    }
}
