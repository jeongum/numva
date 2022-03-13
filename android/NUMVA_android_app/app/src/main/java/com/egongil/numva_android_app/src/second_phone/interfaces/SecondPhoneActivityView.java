package com.egongil.numva_android_app.src.second_phone.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.second_phone.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneResponse;

public interface SecondPhoneActivityView {

    void getSecondPhoneSuccess(GetSecondPhoneResponse getSecondPhoneResponse, ErrorResponse errorResponse);
    void getSecondPhoneFailure();

    void repSecondPhoneSuccess(RepSecondPhoneResponse repSecondPhoneResponse, ErrorResponse errorResponse);
    void repSecondPhoneFailure();

    void deleteSecondPhoneSuccess(DeleteSecondPhoneResponse deleteSecondPhoneResponse, ErrorResponse errorResponse);
    void deleteSecondPhoneFailure();


}
