package com.egongil.numva_android_app.src.edit_userinfo.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.DeleteAccountResponse;

public interface DeleteAccountActivityView {

    void deleteAccountSuccess(DeleteAccountResponse deleteAccountResponse, ErrorResponse errorResponse);
    void deleteAccountFailure();
}
