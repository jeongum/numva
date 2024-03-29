package com.egongil.numva_android_app.src.qr_scan.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.response.ScanQrResponse;

public interface QrScanResultActivityContract {
    void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse);
    void registerQrFailure();
}
