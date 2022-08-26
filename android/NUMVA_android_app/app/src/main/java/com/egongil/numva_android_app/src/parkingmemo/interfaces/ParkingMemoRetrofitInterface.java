package com.egongil.numva_android_app.src.parkingmemo.interfaces;

import com.egongil.numva_android_app.src.config.models.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.config.models.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.UpdateSimpleMemoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ParkingMemoRetrofitInterface {
    @POST("memo/get")
    Call<GetParkingMemoResponse> getParkingMemo(@Body GetParkingMemoRequest params);

    @POST("memo/set")
    Call<GetParkingMemoResponse> setParkingmemo(@Body SetParkingMemoRequest params);

    @GET("quickMemo/get")
    Call<GetSimpleMemoResponse> getSimpleMemo();

    @POST("quickMemo/delete")
    Call<UpdateSimpleMemoResponse> deleteSimpleMemo(@Body DeleteSimpleMemoRequest params);

    @POST("quickMemo/update")
    Call<UpdateSimpleMemoResponse> editSimpleMemo(@Body EditSimpleMemoRequest params);

    @POST("quickMemo/set")
    Call<AddSimpleMemoResponse> addSimpleMemo(@Body AddSimpleMemoRequest params);
}
