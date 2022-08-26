package com.egongil.numva_android_app.src.customer_center.interfaces;

import com.egongil.numva_android_app.src.config.models.FAQResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CustomerCenterRetrofitInterface {
    @GET("faq/getAll")
    Call<FAQResponse> getFAQ();

}
