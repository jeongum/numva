package com.egongil.numva_android_app.src.customer_center;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.customer_center.interfaces.CustomerCenterActivityView;
import com.egongil.numva_android_app.src.customer_center.interfaces.CustomerCenterRetrofitInterface;
import com.egongil.numva_android_app.src.customer_center.models.FAQResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofit;

public class CustomerCenterService {
    private CustomerCenterActivityView mCustomerCenterActivityView;
    final CustomerCenterRetrofitInterface customerCenterRetrofitInterface;

    public CustomerCenterService(CustomerCenterActivityView mCustomerCenterActivityView){
        this.mCustomerCenterActivityView = mCustomerCenterActivityView;
        this.customerCenterRetrofitInterface = getRetrofit().create(CustomerCenterRetrofitInterface.class);
    }


    void getFAQ(){
        customerCenterRetrofitInterface.getFAQ().enqueue(new Callback<FAQResponse>() {
            @Override
            public void onResponse(Call<FAQResponse> call, Response<FAQResponse> response) {
                FAQResponse faqResponse = null;
                ErrorResponse errorResponse = null;
                if(response.body()!=null){
                    faqResponse = response.body();
                }
                else{
                    Converter<ResponseBody, ErrorResponse> errorConverter = getRetrofit().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    try {
                        errorResponse = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
