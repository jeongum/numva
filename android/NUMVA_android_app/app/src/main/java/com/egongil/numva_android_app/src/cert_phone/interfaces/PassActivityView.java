package com.egongil.numva_android_app.src.cert_phone.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.PassResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;

public interface PassActivityView {
    void postPassSuccess(PassResponse passResponse, ErrorResponse errorResponse);
    void postPassFailure();
}
