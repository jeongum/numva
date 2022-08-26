package com.egongil.numva_android_app.src.findLogin.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.FindPwResponse;

public interface ResetPwActivityView {
    void postResetPwSuccess(FindPwResponse findPwResponse, ErrorResponse errorResponse);

    //아이디찾기 post실패 시
    void postResetPwFailure();
}
