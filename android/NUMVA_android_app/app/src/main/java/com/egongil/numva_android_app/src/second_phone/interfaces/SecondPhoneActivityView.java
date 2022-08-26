package com.egongil.numva_android_app.src.second_phone.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.RepSecondPhoneResponse;

public interface SecondPhoneActivityView {

    void getSecondPhoneSuccess(GetSecondPhoneResponse getSecondPhoneResponse, ErrorResponse errorResponse);
    void getSecondPhoneFailure();

    void repSecondPhoneSuccess(RepSecondPhoneResponse repSecondPhoneResponse, ErrorResponse errorResponse);
    void repSecondPhoneFailure();

    void deleteSecondPhoneSuccess(DeleteSecondPhoneResponse deleteSecondPhoneResponse, ErrorResponse errorResponse);
    void deleteSecondPhoneFailure();


}
