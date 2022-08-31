package com.egongil.numva_android_app.src.customer_center.models;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.customer_center.interfaces.CustomerCenterActivityContract;
import com.egongil.numva_android_app.src.config.models.response.FAQResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.convertErrorResponse;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;

public class CustomerCenterService {
    private CustomerCenterActivityContract mCustomerCenterActivityContract;

    public CustomerCenterService(CustomerCenterActivityContract mCustomerCenterActivityContract){
        this.mCustomerCenterActivityContract = mCustomerCenterActivityContract;
    }

    public void getFAQ(){
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
                mCustomerCenterActivityContract.getFAQSuccess(faqResponse, errorResponse);
            }

            @Override
            public void onFailure(Call<FAQResponse> call, Throwable t) {
                t.printStackTrace();
                mCustomerCenterActivityContract.getFAQFailure();
            }
        });
    }


}
