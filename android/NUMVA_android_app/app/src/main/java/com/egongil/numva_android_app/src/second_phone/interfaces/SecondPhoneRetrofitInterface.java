package com.egongil.numva_android_app.src.second_phone.interfaces;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.second_phone.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.second_phone.models.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SecondPhoneRetrofitInterface {
    @POST("secondPhone/set")
    Call<SetSecondPhoneResponse> setSecondPhone(
            @Body SetSecondPhoneRequest params);

    @GET("secondPhone/get")
    Call<GetSecondPhoneResponse> getSecondPhone();

    @POST("secondPhone/setRep")
    Call<RepSecondPhoneResponse> repSecondPhone(
            @Body RepSecondPhoneRequest params);

    @POST("secondPhone/delete")
    Call<DeleteSecondPhoneResponse> deleteSecondPhone(
            @Body DeleteSecondPhoneRequest params);

    @POST("user/certPhone")
    Call<CertPhoneResponse> postCertPhone(
            @Body CertPhoneRequest params);
}
