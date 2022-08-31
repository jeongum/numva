package com.egongil.numva_android_app.src.qr_management.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.egongil.numva_android_app.src.qr_management.interfaces.QrManagementActivityContract;

public class QrManagementViewModelFactory implements ViewModelProvider.Factory {
    private QrManagementActivityContract mQrManagementActivityContract;

    public QrManagementViewModelFactory(QrManagementActivityContract mQrManagementActivityContract) {
        this.mQrManagementActivityContract = mQrManagementActivityContract;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return(T) new QrManagementViewModel(mQrManagementActivityContract);
    }
}
