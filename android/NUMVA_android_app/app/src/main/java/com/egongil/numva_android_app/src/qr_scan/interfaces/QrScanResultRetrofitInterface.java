package com.egongil.numva_android_app.src.qr_scan.interfaces;

import com.egongil.numva_android_app.src.qr_management.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_scan.models.ScanQrRequest;
import com.egongil.numva_android_app.src.qr_scan.models.ScanQrResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QrScanResultRetrofitInterface {
    @POST("safetyInfo/scanQR")
    Call<ScanQrResponse> scanQr(@Body ScanQrRequest params);

    @POST("safetyInfo/registerQR")
    Call<RegisterQrResponse> registerQr(@Body RegisterQrRequest params);
}
