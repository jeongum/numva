package com.egongil.numva_android_app.src.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.findLogin.FindLoginActivity;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.signup.interfaces.SignupActivityView;
import com.egongil.numva_android_app.src.signup.models.SignupRequest;
import com.egongil.numva_android_app.src.signup.models.SignupResponse;
import com.egongil.numva_android_app.src.signup.models.ValidEmailRequest;
import com.egongil.numva_android_app.src.signup.models.ValidEmailResponse;

import java.util.Random;

public class SignupMoreInfoActivity extends BaseActivity implements SignupActivityView , ConnectionReceiver.ConnectionReceiverListener {

    EditText mEtEmail, mEtPw, mEtCpw, mEtNick;
    Button mBtnDoubleCheck, mBtnRanNick, mBtnSignup;
    TextView mTvEmailDuplicate, mTvCheckPassword, mTvCheckNickname;
    TextView mTvName, mTvPhone, mTvBirth;
    ImageView mIvEmailRemoveBtn, mIvPwRemoveBtn, mIvCpwRemoveBtn, mIvNicknameRemoveBtn, mBtnBack;
    String mStrName, mStrPhone, mStrBirth;
    int mCheckMarketing;
    static Boolean SIGNUP_POSSIBLE = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_more_info);

        checkConnection(); //???????????? ?????? ??????

        mTvName = findViewById(R.id.signup_tv_name);
        mTvPhone = findViewById(R.id.signup_tv_phone);
        mTvBirth = findViewById(R.id.signup_tv_birth);

        mEtEmail = findViewById(R.id.signup_et_email);  //?????????(?????????)
        mEtPw = findViewById(R.id.signup_et_password);  //????????????
        mEtCpw = findViewById(R.id.signup_et_cpassword);  //???????????? ??????
        mEtNick = findViewById(R.id.signup_et_nickname);  //?????????

        mBtnDoubleCheck = findViewById(R.id.signup_btn_doublecheck);  //???????????? ??????
        mBtnRanNick = findViewById(R.id.signup_btn_randomnickname);  //?????? ????????? ??????
        mBtnSignup = findViewById(R.id.signup_btn);  //???????????? ??????

        mTvEmailDuplicate = findViewById(R.id.signup_tv_failure_email);  //????????? fail guide
        mTvCheckPassword = findViewById(R.id.signup_tv_failure_cpw);  //???????????? fail guide
        mTvCheckNickname = findViewById(R.id.signup_tv_failure_nickname);  //????????? fail guide

        mIvEmailRemoveBtn = findViewById(R.id.signup_Iv_email_remove);  //?????????(?????????) ?????? ??????
        mIvPwRemoveBtn = findViewById(R.id.signup_Iv_pw_remove);  //???????????? ?????? ??????
        mIvCpwRemoveBtn = findViewById(R.id.signup_Iv_cpw_remove);  //???????????? ?????? ?????? ??????
        mIvNicknameRemoveBtn = findViewById(R.id.signup_Iv_nickname_remove);  //????????? ?????? ??????
        mBtnBack = findViewById(R.id.signup_iv_backbtn);  //???????????? ??????

        Intent intent = getIntent();
        mCheckMarketing = intent.getIntExtra("CheckMarketing", 0);
        mStrName = intent.getStringExtra("name");
        mStrPhone = intent.getStringExtra("phone");
        mStrBirth = intent.getStringExtra("birth");

        mTvName.setText(mStrName);
        mTvPhone.setText(mStrPhone);
        mTvBirth.setText(mStrBirth);

        //???????????? ??????
        mBtnBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        //???????????? ?????? event
        mBtnDoubleCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                checkDuplicateEmail(email);
            }
        });

        //??????????????? ?????? event
        mBtnRanNick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mEtNick.setText(setRandNick());
            }
        });

        //???????????? ?????? event
        mBtnSignup.setOnClickListener(new OnSingleClickListener(){
            @Override
            public void onSingleClick(View v) {
                if(!checkRegisterAvailable()){
                    return;
                }else{
                    signup();
                }
            }
        });

        /////////////EditText x?????? ?????????///////////////////
        mIvEmailRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtEmail.setText("");
            }
        });
        mIvPwRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtPw.setText("");
            }
        });
        mIvCpwRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtCpw.setText("");
            }
        });
        mIvNicknameRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtNick.setText("");
            }
        });

        ///////////////////////////////////////////////////////////////

        mEtNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvNicknameRemoveBtn.getVisibility()==View.GONE){
                    mIvNicknameRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvNicknameRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvNicknameRemoveBtn.setVisibility(View.GONE);
                }
            }
        });
        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvEmailRemoveBtn.getVisibility()==View.GONE){
                    mIvEmailRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvEmailRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvEmailRemoveBtn.setVisibility(View.GONE);
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
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvPwRemoveBtn.getVisibility()==View.GONE){
                    mIvPwRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvPwRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvPwRemoveBtn.setVisibility(View.GONE);
                }
            }
        });
        mEtCpw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvCpwRemoveBtn.getVisibility()==View.GONE){
                    mIvCpwRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvCpwRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvCpwRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

    }


    private void signup(){

        SignupService signupService = new SignupService(this);
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName(mStrName);
        signupRequest.setEmail(mEtEmail.getText().toString());
        signupRequest.setPassword(mEtPw.getText().toString());
        signupRequest.setPhone(mStrPhone);
        signupRequest.setBirth(mStrBirth);
        signupRequest.setNickname(mEtNick.getText().toString());
        signupRequest.setAgreement_marketing(mCheckMarketing);

        signupService.postSignup(signupRequest);
    }

    //????????? ???????????? ?????????
    private void checkDuplicateEmail(String email){
        SignupService signupService = new SignupService(this);
        ValidEmailRequest validEmailRequest = new ValidEmailRequest();

        validEmailRequest.setEmail(email);
        signupService.postValidEmail(validEmailRequest);
    }

    @SuppressLint("ResourceAsColor")
    public void signupSuccessGuide(TextView t, boolean state){
        if(t.equals(mTvEmailDuplicate)){
            if(state==true) {
                t.setText(getString(R.string.signup_id_success_guide));
                t.setTextColor(R.color.colorPrimary);
                t.setVisibility(View.VISIBLE);
            }else{
                t.setText(getString(R.string.signup_id_fail_guide));
                t.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean checkRegisterAvailable(){
        //id????????????, pwc????????????, ???????????????, ???????????? ??? ?????? ??????
        String nowNick = mEtNick.getText().toString();

        Boolean registerAvailable = true;

        //???????????? ??????
        if (mEtPw.getText().toString().equals(mEtCpw.getText().toString()) && mEtPw.getText().length()!=0) {
            mTvCheckPassword.setVisibility(View.GONE);
        }else if(mEtPw.getText().length()==0) {
            //???????????? ??? ???????????? ???
            mTvCheckPassword.setVisibility(View.GONE);
            registerAvailable = false;
            mEtPw.requestFocus();
        }else{
            mTvCheckPassword.setVisibility(View.VISIBLE);
            registerAvailable = false;
            mEtPw.requestFocus();  //???????????? EditText??? focus
        }

        //?????????
        if(nowNick.equals("") || !nowNick.matches("^[a-zA-Z0-9???-??????-???]+$")){
            mTvCheckNickname.setVisibility(View.VISIBLE);
            registerAvailable=false;
            mEtNick.requestFocus(); //????????? EditText??? focus
        }else{
            mTvCheckNickname.setVisibility(View.GONE);
        }

        //???????????? ??? ??????
        if(mEtEmail.getText().length()==0 || mEtPw.getText().length()==0 || mEtCpw.getText().length()==0 || mEtNick.getText().length()==0){
            registerAvailable = false;
            showCustomToast("?????? ????????? ??????????????????.");
        }

        if(registerAvailable){
            return true;
        }else{
            return false;
        }

    }

    //??????????????? ?????????
    private String setRandNick(){
        Random rand = new Random();
        int n, m ;
        String strnick1, strnick2;

        String[] nick1 = getResources().getStringArray(R.array.nick1);
        n = rand.nextInt(nick1.length-1);
        strnick1 = nick1[n];

        String[] nick2 = getResources().getStringArray(R.array.nick2);
        m = rand.nextInt(nick2.length-1);
        strnick2 = nick2[m];

        return strnick1+strnick2;
    }



    //???????????? ?????? post ??????
    @Override
    public void postSignupSuccess(SignupResponse signupResponse, ErrorResponse errorResponse) {
        if(signupResponse != null){
            if(signupResponse.getCode()==200 && signupResponse.isSuccess()){
                Intent intent = new Intent(SignupMoreInfoActivity.this, SignupDoneActivity.class);
                startActivity(intent);
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode() == -102){
                //name, phone, birth?????? ????????? ???
                TwoButtonDialog alreadyExistDialog = new TwoButtonDialog(this);
                alreadyExistDialog.showDialog();
                alreadyExistDialog.setContextText("?????? ?????????????????? ???????????????.");
                alreadyExistDialog.setSelectText("ID??????", "?????????");
                alreadyExistDialog.mBtnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ID??????
                        Intent intent = new Intent(SignupMoreInfoActivity.this, FindLoginActivity.class);
                        startActivity(intent);
                    }
                });
                alreadyExistDialog.mBtnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //?????????
                        Intent intent = new Intent(SignupMoreInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (SIGNUP_POSSIBLE.equals(false)) {
                showCustomToast("????????? ??????????????? ????????????.");
                mEtEmail.requestFocus();
            }
        }
    }

    @Override
    public void postSignupFailure() {

    }


    //????????? ???????????? post ??????
    @Override
    public void postValidEmailSuccess(ValidEmailResponse validEmailResponse, ErrorResponse errorResponse) {
        if(validEmailResponse != null) {  //body??? ????????? ??? (???????????? 200)
            if (validEmailResponse.getCode() == 200 && validEmailResponse.isSuccess()) {
                //????????? ???????????? ?????? ??? ??? ??? ?????? (?????? ????????? ???)
                showCustomToast("??????????????? ??????????????????.");
                SIGNUP_POSSIBLE = true;  //???????????? ????????? ??????
                signupSuccessGuide(mTvEmailDuplicate, SIGNUP_POSSIBLE);
            }
        }
        else if(errorResponse!=null) {
            SIGNUP_POSSIBLE = false;  //???????????? ???????????? ??????
            showCustomToast("?????? ???????????? ??????????????????.");
            signupSuccessGuide(mTvEmailDuplicate, SIGNUP_POSSIBLE);
        }
    }

    @Override
    public void postValidEmailFailure() {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }else{
            //dismiss the dialog or refresh the activity
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //register connection status listener
        ApplicationClass.getInstance().setConnectionListener(this);

    }

    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }
}