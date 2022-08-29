package com.egongil.numva_android_app.src.findLogin.interfaces;

import com.egongil.numva_android_app.src.config.models.response.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.FindIdResponse;

public interface FindIdActivityView {

    //FindIdFragment에서 implements해서 사용할 인터페이스

    //아이디찾기 post성공 시
    void postFindIdSuccess(FindIdResponse findIdResponse, ErrorResponse errorResponse);

    //아이디찾기 post실패 시
    void postFindIdFailure();

    void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse);

    void postCertPhoneFailure();
}
