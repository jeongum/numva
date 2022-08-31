package com.egongil.numva_android_app.src.config.interfaces;

import com.egongil.numva_android_app.src.config.models.request.CertPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.PassRequest;
import com.egongil.numva_android_app.src.config.models.response.PassResponse;
import com.egongil.numva_android_app.src.config.models.request.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.DeleteAccountResponse;
import com.egongil.numva_android_app.src.config.models.request.DeleteQrRequest;
import com.egongil.numva_android_app.src.config.models.response.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.request.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.EditUserInfoRequest;
import com.egongil.numva_android_app.src.config.models.response.EditUserInfoResponse;
import com.egongil.numva_android_app.src.config.models.response.FAQResponse;
import com.egongil.numva_android_app.src.config.models.request.FindIdRequest;
import com.egongil.numva_android_app.src.config.models.response.FindIdResponse;
import com.egongil.numva_android_app.src.config.models.request.FindPwRequest;
import com.egongil.numva_android_app.src.config.models.response.FindPwResponse;
import com.egongil.numva_android_app.src.config.models.request.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetUserResponse;
import com.egongil.numva_android_app.src.config.models.request.LinkSocialRequest;
import com.egongil.numva_android_app.src.config.models.response.LinkSocialResponse;
import com.egongil.numva_android_app.src.config.models.request.LoginRequest;
import com.egongil.numva_android_app.src.config.models.response.LoginResponse;
import com.egongil.numva_android_app.src.config.models.response.LogoutResponse;
import com.egongil.numva_android_app.src.config.models.request.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.response.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.request.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.ResetPwRequest;
import com.egongil.numva_android_app.src.config.models.request.ScanQrRequest;
import com.egongil.numva_android_app.src.config.models.response.ScanQrResponse;
import com.egongil.numva_android_app.src.config.models.request.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.SetQrNameRequest;
import com.egongil.numva_android_app.src.config.models.response.SetQrNameResponse;
import com.egongil.numva_android_app.src.config.models.request.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.SetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.SignupRequest;
import com.egongil.numva_android_app.src.config.models.response.SignupResponse;
import com.egongil.numva_android_app.src.config.models.request.SocialLoginRequest;
import com.egongil.numva_android_app.src.config.models.response.SocialLoginResponse;
import com.egongil.numva_android_app.src.config.models.request.SocialRegisterRequest;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.config.models.response.UpdateSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.request.ValidEmailRequest;
import com.egongil.numva_android_app.src.config.models.response.ValidEmailResponse;

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

    interface PassRetrofitInterface {
        @POST("pass/getInfo")
        Call<PassResponse> postPass(
                @Body PassRequest params);


    }
}
