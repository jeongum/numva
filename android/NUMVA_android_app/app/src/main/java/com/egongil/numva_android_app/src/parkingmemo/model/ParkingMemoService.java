package com.egongil.numva_android_app.src.parkingmemo.model;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoActivityContract;
import com.egongil.numva_android_app.src.config.models.request.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.request.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.request.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.UpdateSimpleMemoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingMemoService {
    private final ParkingMemoActivityContract mParkingMemoActivityContract;

    public ParkingMemoService(ParkingMemoActivityContract mParkingMemoActivityContract) {
        this.mParkingMemoActivityContract = mParkingMemoActivityContract;
    }

    public void getParkingMemo(GetParkingMemoRequest getParkingMemoRequest){
        getRetrofitService().getParkingMemo(getParkingMemoRequest).enqueue(new Callback<GetParkingMemoResponse>() {
            @Override
            public void onResponse(Call<GetParkingMemoResponse> call, Response<GetParkingMemoResponse> response) {
                GetParkingMemoResponse getParkingMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    getParkingMemoResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mParkingMemoActivityContract.getParkingMemoSuccess(getParkingMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetParkingMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityContract.getParkingMemoFailure();
            }
        });
    }

    public void setParkingMemo(SetParkingMemoRequest setParkingMemoRequest){
        getRetrofitService().setParkingmemo(setParkingMemoRequest).enqueue(new Callback<GetParkingMemoResponse>() {
            @Override
            public void onResponse(Call<GetParkingMemoResponse> call, Response<GetParkingMemoResponse> response) {
                GetParkingMemoResponse getParkingMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    getParkingMemoResponse = response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                mParkingMemoActivityContract.setParkingMemoSuccess(getParkingMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetParkingMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityContract.setParkingMemoFailure();
            }
        });
    }
    public void getSimpleMemo(){
        getRetrofitService().getSimpleMemo().enqueue(new Callback<GetSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<GetSimpleMemoResponse> call, Response<GetSimpleMemoResponse> response) {
                GetSimpleMemoResponse getSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() !=null){
                    getSimpleMemoResponse = response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                mParkingMemoActivityContract.getSimpleMemoSuccess(getSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityContract.getSimpleMemoFailure();
            }
        });
    }
    public void deleteSimpleMemo(DeleteSimpleMemoRequest deleteSimpleMemoRequest){
        getRetrofitService().deleteSimpleMemo(deleteSimpleMemoRequest).enqueue(new Callback<UpdateSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<UpdateSimpleMemoResponse> call, Response<UpdateSimpleMemoResponse> response) {
                UpdateSimpleMemoResponse updateSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    updateSimpleMemoResponse = response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                mParkingMemoActivityContract.deleteSimpleMemoSuccess(updateSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<UpdateSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityContract.deleteSimpleMemoFailure();
            }
        });
    }
    public void editSimpleMemo(EditSimpleMemoRequest editSimpleMemoRequest){
        getRetrofitService().editSimpleMemo(editSimpleMemoRequest).enqueue(new Callback<UpdateSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<UpdateSimpleMemoResponse> call, Response<UpdateSimpleMemoResponse> response) {
                UpdateSimpleMemoResponse updateSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    updateSimpleMemoResponse = response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                mParkingMemoActivityContract.editSimpleMemoSuccess(updateSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<UpdateSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityContract.editSimpleMemoFailure();
            }
        });
    }
    public void addSimpleMemo(AddSimpleMemoRequest addSimpleMemoRequest){
        getRetrofitService().addSimpleMemo(addSimpleMemoRequest).enqueue(new Callback<AddSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<AddSimpleMemoResponse> call, Response<AddSimpleMemoResponse> response) {
                AddSimpleMemoResponse addSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    addSimpleMemoResponse= response.body();
                }else{
                    errorResponse = convertErrorResponse(response);
                }
                mParkingMemoActivityContract.addSimpleMemoSucccess(addSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<AddSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityContract.addSimpleMemoFailure();
            }
        });
    }
}
