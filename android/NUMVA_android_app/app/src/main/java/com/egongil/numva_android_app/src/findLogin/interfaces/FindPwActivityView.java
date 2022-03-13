package com.egongil.numva_android_app.src.findLogin.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.models.FindPwResponse;

public interface FindPwActivityView {
    void postFindPwSuccess(FindPwResponse findPwResponse, ErrorResponse errorResponse);

    //아이디찾기 post실패 시
    void postFindPwFailure();

    void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse);

    void postCertPhoneFailure();
}
