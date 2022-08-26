package com.egongil.numva_android_app.src.edit_userinfo.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.EditUserInfoResponse;

public interface EditUserInfoActivityView {

    void postEditUserInfoSuccess(EditUserInfoResponse editUserInfoResponse, ErrorResponse errorResponse);
    void postEditUserInfoFailure();
}
