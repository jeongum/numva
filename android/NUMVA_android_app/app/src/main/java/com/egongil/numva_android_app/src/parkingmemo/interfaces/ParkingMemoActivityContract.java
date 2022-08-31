package com.egongil.numva_android_app.src.parkingmemo.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.UpdateSimpleMemoResponse;

public interface ParkingMemoActivityContract {
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
