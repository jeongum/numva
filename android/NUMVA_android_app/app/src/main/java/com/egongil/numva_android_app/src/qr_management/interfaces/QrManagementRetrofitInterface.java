package com.egongil.numva_android_app.src.qr_management.interfaces;

import com.egongil.numva_android_app.src.config.models.DeleteQrRequest;
import com.egongil.numva_android_app.src.config.models.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.SetQrNameRequest;
import com.egongil.numva_android_app.src.config.models.SetQrNameResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QrManagementRetrofitInterface {
    @POST("safetyInfo/setName")
    Call<SetQrNameResponse> setQrName(@Body SetQrNameRequest params);

    @POST("safetyInfo/registerQR")
    Call<RegisterQrResponse> registerQr(@Body RegisterQrRequest params);

    @POST("safetyInfo/deleteSI")
    Call<DeleteQrResponse> deleteQr(@Body DeleteQrRequest params);

}
