package com.egongil.numva_android_app.src.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egongil.numva_android_app.BuildConfig;
import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.FindLoginActivity;
import com.egongil.numva_android_app.src.login.interfaces.LoginActivityView;
import com.egongil.numva_android_app.src.login.snslogin.kakaologin.KakaoLogin;
import com.egongil.numva_android_app.src.login.models.LoginRequest;
import com.egongil.numva_android_app.src.login.models.LoginResponse;
import com.egongil.numva_android_app.src.login.models.SocialLoginRequest;
import com.egongil.numva_android_app.src.login.models.SocialLoginResponse;
import com.egongil.numva_android_app.src.login.models.SocialValidEmailErrorResponse;
import com.egongil.numva_android_app.src.login.models.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;
import com.egongil.numva_android_app.src.login.snslogin.naverlogin.NaverLogin;
import com.egongil.numva_android_app.src.main.MainActivity;

import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.signup.SignupTermsActivity;

import com.egongil.numva_android_app.src.signup.models.ValidEmailRequest;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import static com.egongil.numva_android_app.src.config.ApplicationClass.FragmentType.FIND_ID_FRAGMENT;
import static com.egongil.numva_android_app.src.config.ApplicationClass.FragmentType.FIND_PW_FRAGMENT;
import static com.egongil.numva_android_app.src.config.ApplicationClass.MESIBO_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_EMAIL;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getGlobalUserLoginInfo;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class LoginActivity extends BaseActivity implements LoginActivityView, ConnectionReceiver.ConnectionReceiverListener {

    private String TAG = "LOGIN_ACTIVITY";
    public static Context mContext;

    //sns login
    Intent secondIntent;
    String snsLoginProvider;

    //kakao login
    Button mBtnKakaoLogin;
    LoginButton mBtnKaKaoLoginBasic;
    KakaoLogin.KaKaoSessionCallback sessionCallback;

    //naver login
    private OAuthLoginButton mNaverLoginBtnBasic;
    private OAuthLogin mNaverLoginModule;


    EditText mEtId, mEtPw;
    Button mBtnLogin;
    ImageView mIvIdRemovebtn, mIvPwRemovebtn;
    TextView mTxtFindId, mTxtFindPw, mTxtSignUp;
    LinearLayout mLlLoginFailGuide;

    private Disposable disposable;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkConnection(); //???????????? ?????? ??????

        mContext = getApplicationContext();

        this.mContext = this;

        View mRootView = findViewById(R.id.login_scrollview);
        //EditText ??? ???????????? ????????? ????????? focus ??????????????? ???
        editTextSetCancelable(mRootView, LoginActivity.this);

        mEtId = findViewById(R.id.login_et_id);
        mEtPw = findViewById(R.id.login_et_pw);
        mIvIdRemovebtn = findViewById(R.id.login_iv_id_removebtn);
        mIvPwRemovebtn = findViewById(R.id.login_iv_pw_removebtn);
        mBtnLogin = findViewById(R.id.login_btn_login);
//        mBtnNaverLogin = findViewById(R.id.login_btn_naver);

        mTxtFindId = findViewById(R.id.login_btn_find_id);
        mTxtFindPw = findViewById(R.id.login_btn_find_pw);
        mTxtSignUp = findViewById(R.id.login_btn_signup);

        //KaKao Login//////////////////
        mBtnKakaoLogin = findViewById(R.id.login_btn_kakao);
        mBtnKaKaoLoginBasic = findViewById(R.id.login_btn_kakao_basic);

        mBtnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnKaKaoLoginBasic.performClick();
                snsLoginProvider = "kakao";
            }
        });
        //////////////////////////////////

        //naver login//////////////////
        mNaverLoginModule = OAuthLogin.getInstance();
        mNaverLoginModule.init(
                this
                , BuildConfig.NAVER_CLIENT_ID
                , BuildConfig.NAVER_CLIENT_SECRET
                , "????????? ???????????? ?????????"
        );
        mNaverLoginBtnBasic = findViewById(R.id.login_btn_naver);
        mNaverLoginBtnBasic.setOAuthLoginHandler(mNaverLoginHandler);
        //////////////////////////////////

        //snsLogin
        if (!HasKakaoSession() && !HasNaverSession()) {
            sessionCallback = new KakaoLogin.KaKaoSessionCallback(getApplicationContext(), LoginActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
            //???????????? ?????? ??????????????? ????????????.
        } else if (HasKakaoSession()) {
            sessionCallback = new KakaoLogin.KaKaoSessionCallback(getApplicationContext(), LoginActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
            Session.getCurrentSession().checkAndImplicitOpen();
            snsLoginProvider = "kakao";
        } else if (HasNaverSession()) {
            Intent intent = new Intent(LoginActivity.this, NaverLogin.class);
            startActivity(intent);
            finish();
            snsLoginProvider = "naver";
        }
        //////////////////////////////////

        mTxtFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindLoginActivity.class);
                intent.putExtra("fragment_name", FIND_ID_FRAGMENT);
                startActivity(intent);
            }
        });

        mTxtFindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindLoginActivity.class);
                intent.putExtra("fragment_name", FIND_PW_FRAGMENT);
                startActivity(intent);
            }
        });

        mTxtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupTermsActivity.class);
                startActivity(intent);
            }
        });
        mLlLoginFailGuide = findViewById(R.id.login_ll_failure_guide);

        mEtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && mIvIdRemovebtn.getVisibility() == View.GONE) {
                    //????????? ?????? ??????????????????, gone ????????????
                    mIvIdRemovebtn.setVisibility(View.VISIBLE);
                } else if (editable.length() == 0 && mIvIdRemovebtn.getVisibility() == View.VISIBLE) {
                    mIvIdRemovebtn.setVisibility(View.GONE);
                }
            }
        });
        mEtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && mIvPwRemovebtn.getVisibility() == View.GONE) {
                    //????????? ?????? ??????????????????, gone ????????????
                    mIvPwRemovebtn.setVisibility(View.VISIBLE);
                } else if (editable.length() == 0 && mIvPwRemovebtn.getVisibility() == View.VISIBLE) {
                    mIvPwRemovebtn.setVisibility(View.GONE);
                }
            }
        });

        mIvIdRemovebtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtId.setText("");
            }
        });
        mIvPwRemovebtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtPw.setText("");
            }
        });
        mBtnLogin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                login();
            }
        });
    }

    //kakao login///////////
    private boolean HasKakaoSession() {
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            return false;
        }
        return true;
    }

    public void directToSecondActivity(Boolean result) {
        secondIntent = new Intent(LoginActivity.this, SnsLoginActivity.class);
        secondIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (result) {
            Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
            String email = getGlobalUserLoginInfo().get(USER_EMAIL);
            isValidEmail(email);
        }
    }

    /////////////////////

    //naver login///////////
    private boolean HasNaverSession() {
        if (OAuthLoginState.NEED_LOGIN.equals(mNaverLoginModule.getState(getApplicationContext())) ||
                OAuthLoginState.NEED_INIT.equals(mNaverLoginModule.getState(getApplicationContext()))) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // ?????? ?????? ??????
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mNaverLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                snsLoginProvider = "naver";
                Intent intent = new Intent(LoginActivity.this, NaverLogin.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Naver Login Failed!", Toast.LENGTH_LONG);
            }
        }
    };

    private void login() {
        LoginService loginService = new LoginService(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mEtId.getText().toString());
        loginRequest.setPassword(mEtPw.getText().toString());
        loginService.postLogin(loginRequest);

        //failure_guide visible?????? gone?????? ?????????.
        if (mLlLoginFailGuide.getVisibility() == View.VISIBLE) {
            mLlLoginFailGuide.setVisibility(View.GONE);
        }
    }

    @Override
    public void postLoginSuccess(LoginResponse loginResponse, ErrorResponse errorResponse) {
        if (loginResponse != null) {
            //????????? ?????? ???
            if (loginResponse.getCode() == 200 && loginResponse.isSuccess()) {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(X_ACCESS_TOKEN, loginResponse.getAccessToken());
                editor.putString(MESIBO_TOKEN, loginResponse.getMesiboToken());
                editor.commit();

                System.out.println("TOKEN: " + loginResponse.getAccessToken());
                System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));
                System.out.println("MESIBO_TOKEN: " + sSharedPreferences.getString(MESIBO_TOKEN, "NULL"));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (errorResponse != null) {
            if (errorResponse.getCode() == -103) {
                //failure_guide visible??? ?????????.
                mLlLoginFailGuide.setVisibility(View.VISIBLE);
            } else {
                showCustomToast(getResources().getString(R.string.login_failure_toast));
            }
        }
    }

    @Override
    public void postLoginFailure() {
        showCustomToast(getResources().getString(R.string.network_error));

    }


    public void isValidEmail(String email) { //????????? ???????????? ?????????
        LoginService loginService = new LoginService(this);
        ValidEmailRequest validEmailRequest = new ValidEmailRequest();
        validEmailRequest.setEmail(email);
        loginService.isValidEmail(validEmailRequest);
    }

    @Override
    public void isValidEmailSuccess(SocialValidEmailResponse
                                            validEmailResponse, SocialValidEmailErrorResponse errorResponse) {
        if (validEmailResponse != null) {  //body??? ????????? ??? (???????????? 200)
            if (validEmailResponse.getCode() == 200 && validEmailResponse.isSuccess()) {
//                ???????????? ?????? ???????????? ??????
                secondIntent.putExtra("fragment", SnsLoginActivity.MORE_INFO);
                startActivity(secondIntent);
                finish();
            }
        } else if (errorResponse != null) {
            if (errorResponse.getCode() == -801) {
//                ?????? ????????? ???????????? ??????
                //?????? ????????? ????????? sns??? ?????????????????? ??????: ????????? ??????
                if (snsLoginProvider.equals(errorResponse.getProvider())) {
                    socialLogin(snsLoginProvider, errorResponse.getSocialId());
                }
                //????????? ???????????? ??????????????? ?????? ?????? sns??? ??????: ?????? ???????????? ??????
                else {
                    secondIntent.putExtra("fragment", SnsLoginActivity.EXIST_EMAIL);
                    startActivity(secondIntent);
                    finish();
                }
            }
        }
    }

    @Override
    public void isValidEmailFailure() {
    }

    public void socialLogin(String provider, String social_id) {
        LoginService loginService = new LoginService(this);
        SocialLoginRequest socialLoginRequest = new SocialLoginRequest(provider, social_id);

        loginService.socialLogin(socialLoginRequest);
    }

    @Override
    public void socialLoginSucceess(SocialLoginResponse
                                            socialLoginResponse, ErrorResponse errorResponse) {
        if (socialLoginResponse != null) {
            if (socialLoginResponse.getCode() == 200 && socialLoginResponse.isSuccess()) {
                //?????? ??????
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(X_ACCESS_TOKEN, socialLoginResponse.getAccessToken());
                editor.putString(MESIBO_TOKEN, socialLoginResponse.getMesiboToken());
                editor.commit();

                System.out.println("TOKEN: " + socialLoginResponse.getAccessToken());
                System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));
                System.out.println("MESIBO_TOKEN: " + sSharedPreferences.getString(MESIBO_TOKEN, "NULL"));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (errorResponse != null) {
            showCustomToast(getResources().getString(R.string.login_failure_toast));
        }
    }

    @Override
    public void socialLoginFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationClass.getInstance().setConnectionListener(this);
    }

    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if (!isConnected) {
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
        showCustomToast(getResources().getString(R.string.network_error));
    }
}
