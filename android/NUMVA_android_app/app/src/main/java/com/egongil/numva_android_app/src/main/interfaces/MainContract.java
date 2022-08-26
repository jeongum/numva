package com.egongil.numva_android_app.src.main.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.GetUserResponse;

public interface MainContract {
    void getUserSuccess(GetUserResponse getUserResponse, ErrorResponse errorResonse);
    void getUserFailure();
}
