package com.egongil.numva_android_app.src.cert_phone.interfaces;

import com.egongil.numva_android_app.src.config.models.response.PassResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;

public interface PassActivityContract {
    void postPassSuccess(PassResponse passResponse, ErrorResponse errorResponse);
    void postPassFailure();
}
