package com.egongil.numva_android_app.src.second_phone.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneResponse;

public interface SecondPhoneRegisterActivityView {

    void setSecondPhoneSuccess(SetSecondPhoneResponse setSecondPhoneResponse, ErrorResponse errorResponse);

    void setSecondPhoneFailure();

    void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse);

    void postCertPhoneFailure();
}
