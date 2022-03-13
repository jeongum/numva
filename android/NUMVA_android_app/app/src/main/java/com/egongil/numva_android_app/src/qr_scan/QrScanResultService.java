package com.egongil.numva_android_app.src.qr_scan;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

import android.util.Log;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoRetrofitInterface;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanResultActivityView;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanResultRetrofitInterface;
import com.egongil.numva_android_app.src.qr_scan.models.ScanQrRequest;
import com.egongil.numva_android_app.src.qr_scan.models.ScanQrResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class QrScanResultService {
    private final QrScanResultActivityView mQrScanResultActivityView;
    final QrScanResultRetrofitInterface qrScanResultRetrofitInterface;

    public QrScanResultService(QrScanResultActivityView mQrScanResultActivityView) {
        this.mQrScanResultActivityView = mQrScanResultActivityView;
        qrScanResultRetrofitInterface = getRetrofit().create(QrScanResultRetrofitInterface.class);
    }
    public void scanQr(ScanQrRequest scanQrRequest){
        qrScanResultRetrofitInterface.scanQr(scanQrRequest).enqueue(new Callback<ScanQrResponse>() {
            @Override
            public void onResponse(Call<ScanQrResponse> call, Response<ScanQrResponse> response) {
                ScanQrResponse scanQrResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    scanQrResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        qrScanResultRetrofitInterface.registerQr(registerQrRequest).enqueue(new retrofit2.Callback<RegisterQrResponse>(){
            @Override
            public void onResponse(Call<RegisterQrResponse> call, Response<RegisterQrResponse> response) {
                Log.e("response.code()", String.valueOf(response.code()));
                RegisterQrResponse registerQrResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    registerQrResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
