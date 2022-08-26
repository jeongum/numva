package com.egongil.numva_android_app.src.qr_management.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.SetQrNameResponse;

public interface QrManagementActivityView {

    void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse);
    void getSafetyInfoFailure();

    void setQrNameSuccess(SetQrNameResponse setQrNameResponse, ErrorResponse errorResponse);
    void setQrNameFailure();

    void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse);
    void registerQrFailure();

    void deleteQrSuccess(DeleteQrResponse deleteQrResponse, ErrorResponse errorResponse);
    void deleteQrFailure();

}
