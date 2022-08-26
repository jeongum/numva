package com.egongil.numva_android_app.src.qr_scan;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import android.util.Log;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.config.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanResultActivityView;
import com.egongil.numva_android_app.src.config.models.ScanQrRequest;
import com.egongil.numva_android_app.src.config.models.ScanQrResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrScanResultService {
    private final QrScanResultActivityView mQrScanResultActivityView;

    public QrScanResultService(QrScanResultActivityView mQrScanResultActivityView) {
        this.mQrScanResultActivityView = mQrScanResultActivityView;
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
                mQrScanResultActivityView.scanQrSuccess(scanQrResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<ScanQrResponse> call, Throwable t) {
                t.printStackTrace();
                mQrScanResultActivityView.scanQrFailure();
            }
        });
    }

    void registerQr(RegisterQrRequest registerQrRequest){
        getRetrofitService().registerQr(registerQrRequest).enqueue(new retrofit2.Callback<RegisterQrResponse>(){
            @Override
            public void onResponse(Call<RegisterQrResponse> call, Response<RegisterQrResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));
                RegisterQrResponse registerQrResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    registerQrResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mQrScanResultActivityView.registerQrSuccess(registerQrResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<RegisterQrResponse> call, Throwable t) {
                t.printStackTrace();
                mQrScanResultActivityView.registerQrFailure();
            }
        });
    }
}
