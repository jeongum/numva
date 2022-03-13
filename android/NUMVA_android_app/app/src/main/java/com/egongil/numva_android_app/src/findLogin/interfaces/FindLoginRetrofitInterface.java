package com.egongil.numva_android_app.src.findLogin.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.findLogin.models.FindIdRequest;
import com.egongil.numva_android_app.src.findLogin.models.FindIdResponse;
import com.egongil.numva_android_app.src.findLogin.models.FindPwRequest;
import com.egongil.numva_android_app.src.findLogin.models.FindPwResponse;
import com.egongil.numva_android_app.src.findLogin.models.ResetPwRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FindLoginRetrofitInterface {
    @POST("user/findEmail")
    Call<FindIdResponse> postFindId(
            @Body FindIdRequest params);

    @POST("user/certForPW")
    Call<FindPwResponse> postFindPw(
            @Body FindPwRequest params);

    @POST("user/resetPW")
    Call<FindPwResponse> postResetPw(
            @Body ResetPwRequest params);

    @POST("user/certPhone")
    Call<CertPhoneResponse> postCertPhone(
            @Body CertPhoneRequest params);

}
