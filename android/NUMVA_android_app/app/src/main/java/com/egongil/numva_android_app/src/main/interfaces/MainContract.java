package com.egongil.numva_android_app.src.main.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;

public interface MainContract {
    void getUserSuccess(RetrofitService.GetUserResponse getUserResponse, ErrorResponse errorResonse);
    void getUserFailure();
}
