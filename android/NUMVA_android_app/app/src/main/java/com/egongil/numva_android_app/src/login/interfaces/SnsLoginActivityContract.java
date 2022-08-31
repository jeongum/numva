package com.egongil.numva_android_app.src.login.interfaces;

import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.LinkSocialResponse;

public interface SnsLoginActivityContract {
    void linkSocialSuccess(LinkSocialResponse linkSocialResponse, ErrorResponse errorResponse);
    void linkSocialFailure();

    void socialRegisterSuccess(LinkSocialResponse linkSocialResponse, ErrorResponse errorResponse);
    void socialRegisterFailure();

}
