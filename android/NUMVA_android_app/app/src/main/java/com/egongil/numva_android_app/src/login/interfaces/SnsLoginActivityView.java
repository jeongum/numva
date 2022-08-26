package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.LinkSocialResponse;

public interface SnsLoginActivityView {
    void linkSocialSuccess(LinkSocialResponse linkSocialResponse, ErrorResponse errorResponse);
    void linkSocialFailure();

    void socialRegisterSuccess(LinkSocialResponse linkSocialResponse, ErrorResponse errorResponse);
    void socialRegisterFailure();

}
