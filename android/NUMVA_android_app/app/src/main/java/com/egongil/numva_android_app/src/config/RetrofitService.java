package com.egongil.numva_android_app.src.config;

import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.DeleteAccountResponse;
import com.egongil.numva_android_app.src.config.models.DeleteQrRequest;
import com.egongil.numva_android_app.src.config.models.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.EditUserInfoRequest;
import com.egongil.numva_android_app.src.config.models.EditUserInfoResponse;
import com.egongil.numva_android_app.src.config.models.FAQResponse;
import com.egongil.numva_android_app.src.config.models.FindIdRequest;
import com.egongil.numva_android_app.src.config.models.FindIdResponse;
import com.egongil.numva_android_app.src.config.models.FindPwRequest;
import com.egongil.numva_android_app.src.config.models.FindPwResponse;
import com.egongil.numva_android_app.src.config.models.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.config.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.GetUserResponse;
import com.egongil.numva_android_app.src.config.models.LinkSocialRequest;
import com.egongil.numva_android_app.src.config.models.LinkSocialResponse;
import com.egongil.numva_android_app.src.config.models.LoginRequest;
import com.egongil.numva_android_app.src.config.models.LoginResponse;
import com.egongil.numva_android_app.src.config.models.LogoutResponse;
import com.egongil.numva_android_app.src.config.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.ResetPwRequest;
import com.egongil.numva_android_app.src.config.models.ScanQrRequest;
import com.egongil.numva_android_app.src.config.models.ScanQrResponse;
import com.egongil.numva_android_app.src.config.models.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.SetQrNameRequest;
import com.egongil.numva_android_app.src.config.models.SetQrNameResponse;
import com.egongil.numva_android_app.src.config.models.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.SetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.SignupRequest;
import com.egongil.numva_android_app.src.config.models.SignupResponse;
import com.egongil.numva_android_app.src.config.models.SocialLoginRequest;
import com.egongil.numva_android_app.src.config.models.SocialLoginResponse;
import com.egongil.numva_android_app.src.config.models.SocialRegisterRequest;
import com.egongil.numva_android_app.src.config.models.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.config.models.UpdateSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.ValidEmailRequest;
import com.egongil.numva_android_app.src.config.models.ValidEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {
    @GET("user/user")
    Call<GetUserResponse> getUser();

    @GET("safetyInfo/getSI")
    Call<GetSafetyInfoResponse> getSafetyInfo();

    @GET("auth/logout")
    Call<LogoutResponse> getLogout();

    @GET("faq/getAll")
    Call<FAQResponse> getFAQ();

    @DELETE("auth/delete")
    Call<DeleteAccountResponse> deleteAccount();

    @POST("user/edit")
    Call<EditUserInfoResponse> postEditUserInfo(
            @Body EditUserInfoRequest params);

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

    @POST("auth/linkSocial")
    Call<LinkSocialResponse> linkSocial(@Body LinkSocialRequest params);

    @POST("auth/socialRegister")
    Call<LinkSocialResponse> socialRegister(@Body SocialRegisterRequest params);


    @POST("auth/login")
    Call<LoginResponse> postLogin(@Body LoginRequest params);

    @POST("user/socialValidEmail")
    Call<SocialValidEmailResponse> isValidEmail(@Body ValidEmailRequest params);

    @POST("auth/socialLogin")
    Call<SocialLoginResponse> socialLogin(@Body SocialLoginRequest params);


    @POST("memo/get")
    Call<GetParkingMemoResponse> getParkingMemo(@Body GetParkingMemoRequest params);

    @POST("memo/set")
    Call<GetParkingMemoResponse> setParkingmemo(@Body SetParkingMemoRequest params);

    @GET("quickMemo/get")
    Call<GetSimpleMemoResponse> getSimpleMemo();

    @POST("quickMemo/delete")
    Call<UpdateSimpleMemoResponse> deleteSimpleMemo(@Body DeleteSimpleMemoRequest params);

    @POST("quickMemo/update")
    Call<UpdateSimpleMemoResponse> editSimpleMemo(@Body EditSimpleMemoRequest params);

    @POST("quickMemo/set")
    Call<AddSimpleMemoResponse> addSimpleMemo(@Body AddSimpleMemoRequest params);

    @POST("safetyInfo/setName")
    Call<SetQrNameResponse> setQrName(@Body SetQrNameRequest params);

    @POST("safetyInfo/registerQR")
    Call<RegisterQrResponse> registerQr(@Body RegisterQrRequest params);

    @POST("safetyInfo/deleteSI")
    Call<DeleteQrResponse> deleteQr(@Body DeleteQrRequest params);

    @POST("safetyInfo/scanQR")
    Call<ScanQrResponse> scanQr(@Body ScanQrRequest params);

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

    @POST("auth/register")
    Call<SignupResponse> postSignup(
            @Body SignupRequest params);

    @POST("user/validEmail")
    Call<ValidEmailResponse> postValidEmail(
            @Body ValidEmailRequest params);

}
