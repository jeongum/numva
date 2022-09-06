package com.egongil.numva_android_app.src.qr_scan.models;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import android.util.Log;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.request.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.response.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanActivityContract;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanResultActivityContract;
import com.egongil.numva_android_app.src.config.models.request.ScanQrRequest;
import com.egongil.numva_android_app.src.config.models.response.ScanQrResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrScanService {
    private final QrScanActivityContract mQrScanActivityContract;

    public QrScanService(QrScanActivityContract mQrScanActivityContract) {
        this.mQrScanActivityContract = mQrScanActivityContract;
    }
    public void scanQr(ScanQrRequest scanQrRequest){
        getRetrofitService().scanQr(scanQrRequest).enqueue(new Callback<ScanQrResponse>() {
            @Override
            public void onResponse(Call<ScanQrResponse> call, Response<ScanQrResponse> response) {
                ScanQrResponse scanQrResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    scanQrResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mQrScanActivityContract.scanQrSuccess(scanQrResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<ScanQrResponse> call, Throwable t) {
                t.printStackTrace();
                mQrScanActivityContract.scanQrFailure();
            }
        });
    }
}
