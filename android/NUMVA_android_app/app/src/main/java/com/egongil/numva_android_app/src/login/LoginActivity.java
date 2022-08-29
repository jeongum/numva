package com.egongil.numva_android_app.src.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.view.FindLoginActivity;
import com.egongil.numva_android_app.src.login.interfaces.LoginActivityView;
import com.egongil.numva_android_app.src.login.snslogin.kakaologin.KakaoLogin;
import com.egongil.numva_android_app.src.config.models.request.LoginRequest;
import com.egongil.numva_android_app.src.config.models.response.LoginResponse;
import com.egongil.numva_android_app.src.config.models.request.SocialLoginRequest;
import com.egongil.numva_android_app.src.config.models.response.SocialLoginResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.SocialValidEmailResponse;
import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;
import com.egongil.numva_android_app.src.login.snslogin.naverlogin.NaverLogin;
import com.egongil.numva_android_app.src.main.view.MainActivity;

import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.signup.SignupTermsActivity;

import com.egongil.numva_android_app.src.config.models.request.ValidEmailRequest;
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

        checkConnection(); //네트워크 연결 확인

        mContext = getApplicationContext();

        this.mContext = this;

        View mRootView = findViewById(R.id.login_scrollview);
        //EditText 밖 클릭하면 키보드 닫히고 focus 해제하도록 함
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
                , "네이버 아이디로 로그인"
        );
        mNaverLoginBtnBasic = findViewById(R.id.login_btn_naver);
        mNaverLoginBtnBasic.setOAuthLoginHandler(mNaverLoginHandler);
        //////////////////////////////////

        //snsLogin
        if (!HasKakaoSession() && !HasNaverSession()) {
            sessionCallback = new KakaoLogin.KaKaoSessionCallback(getApplicationContext(), LoginActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
            //네이버의 경우 핸들러에서 동작한다.
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
                    //한글자 이상 입력되어있고, gone 상태이면
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
                    //한글자 이상 입력되어있고, gone 상태이면
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
            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
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

        // 세션 콜백 삭제
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

        //failure_guide visible이면 gone으로 만든다.
        if (mLlLoginFailGuide.getVisibility() == View.VISIBLE) {
            mLlLoginFailGuide.setVisibility(View.GONE);
        }
    }

    @Override
    public void postLoginSuccess(LoginResponse loginResponse, ErrorResponse errorResponse) {
        if (loginResponse != null) {
            //로그인 성공 시
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
                //failure_guide visible로 만든다.
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


    public void isValidEmail(String email) { //아이디 중복확인 메소드
        LoginService loginService = new LoginService(this);
        ValidEmailRequest validEmailRequest = new ValidEmailRequest();
        validEmailRequest.setEmail(email);
        loginService.isValidEmail(validEmailRequest);
    }

    @Override
    public void isValidEmailSuccess(SocialValidEmailResponse
                                            validEmailResponse, SocialValidEmailErrorResponse errorResponse) {
        if (validEmailResponse != null) {  //body로 넘어올 때 (성공코드 200)
            if (validEmailResponse.getCode() == 200 && validEmailResponse.isSuccess()) {
//                가입되지 않은 이메일일 경우
                secondIntent.putExtra("fragment", SnsLoginActivity.MORE_INFO);
                startActivity(secondIntent);
                finish();
            }
        } else if (errorResponse != null) {
            if (errorResponse.getCode() == -801) {
//                이미 가입된 이메일일 경우
                //지금 로그인 시도한 sns로 가입되어있을 경우: 로그인 진행
                if (snsLoginProvider.equals(errorResponse.getProvider())) {
                    socialLogin(snsLoginProvider, errorResponse.getSocialId());
                }
                //가입된 이메일이 기본이메일 또는 다른 sns일 경우: 연동 페이지로 연결
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
                //토큰 저장
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
