package com.egongil.numva_android_app.src.customer_center;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.customer_center.interfaces.CustomerCenterActivityView;
import com.egongil.numva_android_app.src.config.models.FAQResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class CustomerCenterService {
    private CustomerCenterActivityView mCustomerCenterActivityView;

    public CustomerCenterService(CustomerCenterActivityView mCustomerCenterActivityView){
        this.mCustomerCenterActivityView = mCustomerCenterActivityView;
    }

    void getFAQ(){
        getRetrofitService().getFAQ().enqueue(new Callback<FAQResponse>() {
            @Override
            public void onResponse(Call<FAQResponse> call, Response<FAQResponse> response) {
                FAQResponse faqResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    faqResponse = response.body();
                } else{
                    errorResponse = convertErrorResponse(response);
                }
                mCustomerCenterActivityView.getFAQSuccess(faqResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FAQResponse> call, Throwable t) {
                t.printStackTrace();
                mCustomerCenterActivityView.getFAQFailure();
            }
        });
    }


}
