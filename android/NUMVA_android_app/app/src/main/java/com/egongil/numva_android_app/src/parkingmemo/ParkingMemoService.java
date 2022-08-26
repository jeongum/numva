package com.egongil.numva_android_app.src.parkingmemo;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoActivityView;
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
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingMemoService {
    private final ParkingMemoActivityView mParkingMemoActivityView;

    public ParkingMemoService(ParkingMemoActivityView mParkingMemoActivityView) {
        this.mParkingMemoActivityView = mParkingMemoActivityView;
    }

    void getParkingMemo(GetParkingMemoRequest getParkingMemoRequest){
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
                mParkingMemoActivityView.getParkingMemoSuccess(getParkingMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetParkingMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityView.getParkingMemoFailure();
            }
        });
    }

    void setparkingMemo(SetParkingMemoRequest setParkingMemoRequest){
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
                mParkingMemoActivityView.setParkingMemoSuccess(getParkingMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetParkingMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityView.setParkingMemoFailure();
            }
        });
    }
    void getSimpleMemo(){
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
                mParkingMemoActivityView.getSimpleMemoSuccess(getSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<GetSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityView.getSimpleMemoFailure();
            }
        });
    }
    void deleteSimpleMemo(DeleteSimpleMemoRequest deleteSimpleMemoRequest){
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
                mParkingMemoActivityView.deleteSimpleMemoSuccess(updateSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<UpdateSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityView.deleteSimpleMemoFailure();
            }
        });
    }
    void editSimpleMemo(EditSimpleMemoRequest editSimpleMemoRequest){
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
                mParkingMemoActivityView.editSimpleMemoSuccess(updateSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<UpdateSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityView.editSimpleMemoFailure();
            }
        });
    }
    void addSimpleMemo(AddSimpleMemoRequest addSimpleMemoRequest){
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
                mParkingMemoActivityView.addSimpleMemoSucccess(addSimpleMemoResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<AddSimpleMemoResponse> call, Throwable t) {
                t.printStackTrace();
                mParkingMemoActivityView.addSimpleMemoFailure();
            }
        });
    }
}
