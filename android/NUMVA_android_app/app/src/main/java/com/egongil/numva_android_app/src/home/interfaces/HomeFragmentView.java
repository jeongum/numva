package com.egongil.numva_android_app.src.home.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.home.models.GetSafetyInfoResponse;

public interface HomeFragmentView {
    void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse);
    void getSafetyInfoFailure();
}
