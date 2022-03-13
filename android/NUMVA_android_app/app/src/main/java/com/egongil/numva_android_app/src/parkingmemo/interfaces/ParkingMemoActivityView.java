package com.egongil.numva_android_app.src.parkingmemo.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.UpdateSimpleMemoResponse;

public interface ParkingMemoActivityView {
    void getParkingMemoSuccess(GetParkingMemoResponse getparkingMemoResponse, ErrorResponse errorResponse);
    void getParkingMemoFailure();

    void setParkingMemoSuccess(GetParkingMemoResponse getParkingMemoResponse, ErrorResponse errorResponse);
    void setParkingMemoFailure();

    void getSimpleMemoSuccess(GetSimpleMemoResponse getSimpleMemoResponse, ErrorResponse errorResponse);
    void getSimpleMemoFailure();

    void deleteSimpleMemoSuccess(UpdateSimpleMemoResponse updateSimpleMemoResponse, ErrorResponse errorResponse);
    void deleteSimpleMemoFailure();

    void editSimpleMemoSuccess(UpdateSimpleMemoResponse updateSimpleMemoResponse, ErrorResponse errorResponse);
    void editSimpleMemoFailure();

    void addSimpleMemoSucccess(AddSimpleMemoResponse addSimpleMemoResponse, ErrorResponse errorResponse);
    void addSimpleMemoFailure();
}
