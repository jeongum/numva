package com.egongil.numva_android_app.src.config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.egongil.numva_android_app.src.config.interfaces.RetrofitService;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.mesibo.api.MesiboProfile;
import com.mesibo.calls.api.MesiboCall;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class ApplicationClass extends Application implements MesiboCall.IncomingListener{
public class ApplicationClass extends Application{
    //서버 주소
    public static String BASE_URL = "http://211.37.147.142/api/";

    //sharedPreferences - TOKEN 저장
    public static SharedPreferences sSharedPreferences = null;

    //Shared Preferences key
    public static String TAG = "TEMPLATE_APP";

    //JWT Token key
    public static String X_ACCESS_TOKEN = "Authorization";
    public static String MESIBO_TOKEN = "Mesibo";

    //Retrofit 인스턴스
    //TODO: retrofit private로 만들고, getRetrofit()으로만 불러오기
    private static Retrofit retrofit;
    private static RetrofitService retrofitService;

    //kakao login
    private static volatile ApplicationClass mInstance = null;
    private static List<String> mGlobalUserLoginInfo = new ArrayList<>();

    public static int USER_ID = 0;
    public static int USER_NAME = 1;
    public static int USER_EMAIL = 2;
    public static int USER_PHONE = 3;
    public static int USER_BIRTHYEAR = 4;
    public static int USER_BIRTHDAY = 5;
    public static int USER_PROVIDER = 6;

    public static ApplicationClass getGlobalApplicationContext(){
        if (mInstance == null) {
            throw new IllegalStateException("This Application does not GlobalAuthHelper");
        }
        return mInstance;
    }

    public static List<String> getGlobalUserLoginInfo() {
        return mGlobalUserLoginInfo;
    }

    public static void setGlobalUserLoginInfo(List<String> userLoginInfo) {
        mGlobalUserLoginInfo = userLoginInfo;
    }
    ///////////

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //카카오 간편로그인
        mInstance = this;
        KakaoSDK.init(new ApplicationClass.KakaoSDKAdapter());

        //shared preference
        if(sSharedPreferences == null){
            sSharedPreferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }

        //MesiboCall

//        MesiboCall.getInstance().init(this);
    }

    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if(retrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(new XAccessTokenInterceptor());

            builder.readTimeout(10000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
//                    .addNetworkInterceptor(new XAccessTokenInterceptor())   //JWT 자동 헤더 전송
                    .addInterceptor(loggingInterceptor);

            OkHttpClient client = builder.build();
            //retrofit객체 생성, API service를 create해줌
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitService getRetrofitService(){
        if(retrofitService == null){
            retrofitService = getRetrofit().create(RetrofitService.class);
        }
        return retrofitService;
    }

    public static ErrorResponse convertErrorResponse(Response response){
        ErrorResponse errorResponse = null;
        Converter<ResponseBody, ErrorResponse> errorConverter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        try {
            errorResponse = errorConverter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorResponse;
    }

    public static synchronized ApplicationClass getInstance(){
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener){
        ConnectionReceiver.connectionReceiverListener = listener;
    }

    //RecyclerView ViewType
    public class KakaoSDKAdapter extends KakaoAdapter {
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return ApplicationClass.getGlobalApplicationContext();
                }
            };
        }
    }

    public class ViewType{
        public static final int SIMPLE_MEMO_VIEW = 0;
        public static final int SIMPLE_MEMO_VIEW_ADD = 1;

        public static final int CHAT_CENTER = 0;
        public static final int CHAT_LEFT = 1;
        public static final int CHAT_RIGHT = 2;
        public static final int CHAT_MISSEDCALL = 3;

        public static final int NUMVATALK_CALL = 0;
        public static final int NUMVATALK_EXIT = 1;
        public static final int NUMVATALK_CANCEL = 2;

    }
    public class ActivityType{
        public static final int MAIN_ACTIVITY = 0;
        public static final int PARKING_MEMO_ACTIVITY = 1;
        public static final int EDIT_USERINFO_ACTIVITY = 2;
        public static final int QR_MANAGEMENT_ACTIVITY = 3;
        public static final int SECONDPHONE_REGISTER_ACTIVITY = 4;
    }
    public class FragmentType{
        public static final int FIND_ID_FRAGMENT = 0;
        public static final int FIND_PW_FRAGMENT = 1;
    }
    public class NetworkType{
        public static final String WIFI_STATE = "WIFI";
        public static final String MOBILE_STATE = "MOBILE";
        public static final String NONE_STATE = "NONE";
    }

//    @Override
//    public MesiboCall.CallProperties MesiboCall_OnIncoming(MesiboProfile userProfile, boolean video) {
//        MesiboCall.CallProperties cc = MesiboCall.getInstance().createCallProperties(video);
//        cc.parent = getApplicationContext();
//        cc.user = userProfile;
//        return cc;
//    }
//
//    @Override
//    public boolean MesiboCall_OnShowUserInterface(MesiboCall.Call call, MesiboCall.CallProperties callProperties) {
//        return false;
//    }
//
//    @Override
//    public void MesiboCall_OnError(MesiboCall.CallProperties callProperties, int i) {
//
//    }
//
//    @Override
//    public boolean MesiboCall_onNotify(int type, MesiboProfile profile, boolean video) {
//        String subject = null, message = null;
//
//        if(true)
//            return false;
//
//        if(MesiboCall.MESIBOCALL_NOTIFY_INCOMING == type) {
//
//        } else if(MesiboCall.MESIBOCALL_NOTIFY_MISSED == type) {
//            subject = "Mesibo Missed Call";
//            message = "You missed a mesibo " + (video?"video ":"") + "call from " + profile.getName();
//
//        }
//        return true;
//    }
}


