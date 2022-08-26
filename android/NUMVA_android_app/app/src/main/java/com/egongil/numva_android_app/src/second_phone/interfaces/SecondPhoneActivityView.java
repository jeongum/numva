package com.egongil.numva_android_app.src.second_phone.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.response.RepSecondPhoneResponse;

public interface SecondPhoneActivityView {

    void getSecondPhoneSuccess(GetSecondPhoneResponse getSecondPhoneResponse, ErrorResponse errorResponse);
    void getSecondPhoneFailure();

    void repSecondPhoneSuccess(RepSecondPhoneResponse repSecondPhoneResponse, ErrorResponse errorResponse);
    void repSecondPhoneFailure();

    void deleteSecondPhoneSuccess(DeleteSecondPhoneResponse deleteSecondPhoneResponse, ErrorResponse errorResponse);
    void deleteSecondPhoneFailure();


}
