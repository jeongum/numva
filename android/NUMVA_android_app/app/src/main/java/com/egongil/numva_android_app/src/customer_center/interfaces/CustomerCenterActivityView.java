package com.egongil.numva_android_app.src.customer_center.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.customer_center.models.FAQResponse;

public interface CustomerCenterActivityView {

    void getFAQSuccess(FAQResponse allFAQResponse, ErrorResponse errorResponse);
    void getFAQFailure();



}
