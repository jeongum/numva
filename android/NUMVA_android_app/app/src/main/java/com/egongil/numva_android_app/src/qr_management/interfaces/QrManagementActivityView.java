package com.egongil.numva_android_app.src.qr_management.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.qr_management.models.DeleteQrResponse;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_management.models.SetQrNameResponse;

public interface QrManagementActivityView {

    void getSafetyInfoSuccess(RetrofitService.GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse);
    void getSafetyInfoFailure();

    void setQrNameSuccess(SetQrNameResponse setQrNameResponse, ErrorResponse errorResponse);
    void setQrNameFailure();

    void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse);
    void registerQrFailure();

    void deleteQrSuccess(DeleteQrResponse deleteQrResponse, ErrorResponse errorResponse);
    void deleteQrFailure();

}
