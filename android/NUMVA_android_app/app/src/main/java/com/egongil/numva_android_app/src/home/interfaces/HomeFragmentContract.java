package com.egongil.numva_android_app.src.home.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;

public interface HomeFragmentContract {
    void getSafetyInfoSuccess(RetrofitService.GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse);
    void getSafetyInfoFailure();
}
