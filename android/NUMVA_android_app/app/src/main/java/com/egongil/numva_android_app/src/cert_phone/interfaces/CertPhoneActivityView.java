package com.egongil.numva_android_app.src.cert_phone.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;

public interface CertPhoneActivityView {
    void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse);

    void postCertPhoneFailure();
}
