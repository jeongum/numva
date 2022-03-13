package com.egongil.numva_android_app.src.parkingmemo;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.retrofit;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoActivityView;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoRetrofitInterface;
import com.egongil.numva_android_app.src.parkingmemo.models.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.UpdateSimpleMemoResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class ParkingMemoService {
    private final ParkingMemoActivityView mParkingMemoActivityView;
    final ParkingMemoRetrofitInterface parkingMemoRetrofitInterface;

    public ParkingMemoService(ParkingMemoActivityView mParkingMemoActivityView) {
        this.mParkingMemoActivityView = mParkingMemoActivityView;
        this.parkingMemoRetrofitInterface = getRetrofit().create(ParkingMemoRetrofitInterface.class);
    }

    void getParkingMemo(GetParkingMemoRequest getParkingMemoRequest){
        parkingMemoRetrofitInterface.getParkingMemo(getParkingMemoRequest).enqueue(new Callback<GetParkingMemoResponse>() {
            @Override
            public void onResponse(Call<GetParkingMemoResponse> call, Response<GetParkingMemoResponse> response) {
                GetParkingMemoResponse getParkingMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() != null){
                    getParkingMemoResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        parkingMemoRetrofitInterface.setParkingmemo(setParkingMemoRequest).enqueue(new Callback<GetParkingMemoResponse>() {
            @Override
            public void onResponse(Call<GetParkingMemoResponse> call, Response<GetParkingMemoResponse> response) {
                GetParkingMemoResponse getParkingMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    getParkingMemoResponse = response.body();
                }else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        parkingMemoRetrofitInterface.getSimpleMemo().enqueue(new Callback<GetSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<GetSimpleMemoResponse> call, Response<GetSimpleMemoResponse> response) {
                GetSimpleMemoResponse getSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body() !=null){
                    getSimpleMemoResponse = response.body();
                }else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        parkingMemoRetrofitInterface.deleteSimpleMemo(deleteSimpleMemoRequest).enqueue(new Callback<UpdateSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<UpdateSimpleMemoResponse> call, Response<UpdateSimpleMemoResponse> response) {
                UpdateSimpleMemoResponse updateSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    updateSimpleMemoResponse = response.body();
                }else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        parkingMemoRetrofitInterface.editSimpleMemo(editSimpleMemoRequest).enqueue(new Callback<UpdateSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<UpdateSimpleMemoResponse> call, Response<UpdateSimpleMemoResponse> response) {
                UpdateSimpleMemoResponse updateSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    updateSimpleMemoResponse = response.body();
                }else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        parkingMemoRetrofitInterface.addSimpleMemo(addSimpleMemoRequest).enqueue(new Callback<AddSimpleMemoResponse>() {
            @Override
            public void onResponse(Call<AddSimpleMemoResponse> call, Response<AddSimpleMemoResponse> response) {
                AddSimpleMemoResponse addSimpleMemoResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    addSimpleMemoResponse= response.body();
                }else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
