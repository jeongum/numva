package com.egongil.numva_android_app.src.second_phone.interfaces;

import com.egongil.numva_android_app.src.config.models.response.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.SetSecondPhoneResponse;

public interface SecondPhoneRegisterActivityContract {

    void setSecondPhoneSuccess(SetSecondPhoneResponse setSecondPhoneResponse, ErrorResponse errorResponse);

    void setSecondPhoneFailure();

    void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse);

    void postCertPhoneFailure();
}
