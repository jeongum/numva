package com.egongil.numva_android_app.src.qr_management.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.response.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.response.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.response.SetQrNameResponse;

public interface QrManagementActivityView {

//    void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse);
//    void getSafetyInfoFailure();

    void setQrNameSuccess(SetQrNameResponse setQrNameResponse, ErrorResponse errorResponse);
    void setQrNameFailure();

    void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse);
    void registerQrFailure();

    void deleteQrSuccess(DeleteQrResponse deleteQrResponse, ErrorResponse errorResponse);
    void deleteQrFailure();

}
