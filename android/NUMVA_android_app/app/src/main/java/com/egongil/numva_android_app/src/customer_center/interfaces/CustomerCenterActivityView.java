package com.egongil.numva_android_app.src.customer_center.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.FAQResponse;

public interface CustomerCenterActivityView {

    void getFAQSuccess(FAQResponse allFAQResponse, ErrorResponse errorResponse);
    void getFAQFailure();



}
