package com.egongil.numva_android_app.src.second_phone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.cert_phone.SendSMS;
import com.egongil.numva_android_app.src.cert_phone.TimerView;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityView;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneRegisterActivityView;
import com.egongil.numva_android_app.src.second_phone.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneResponse;

public class SecondPhoneRegisterActivity extends BaseActivity implements SecondPhoneRegisterActivityView, ConnectionReceiver.ConnectionReceiverListener {

    EditText mEtPhoneMid, mEtPhoneFin, mEtCtfNumber;
    Button mBtnSend, mBtnCheck, mBtnRegister;
    String mStPhone, mStPhone_fir, mStCertNum;
    Spinner mPhoneFir;
    ImageView mIvCtfNumRemoveBtn, mIvBack, mIvExit;
    LinearLayout mLlCertNum;
    TextView mTvCtfNumber;

    static Boolean REGISTER_POSSIBLE = false;
    private TimerView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_phone_register);
        checkConnection(); //네트워크 연결 확인

        // 전화번호 첫 3자리 spinner - dropdown
        mPhoneFir = (Spinner)this.findViewById(R.id.secondphone_register_sp_phone_start);
        String[] str = this.getResources().getStringArray(R.array.phone_dropdown);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner_phonenumber, str);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        mPhoneFir.setAdapter(adapter);


        mPhoneFir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //선택된 해당 값 받아오기
                mStPhone_fir = (mPhoneFir.getItemAtPosition(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mEtPhoneMid = findViewById(R.id.secondphone_register_et_phone_middle); //8567
        mEtPhoneFin = findViewById(R.id.secondphone_register_et_phone_final);  //1488
        mEtCtfNumber = findViewById(R.id.secondphone_register_et_certnumber); //인증번호 EditText
        mBtnSend = findViewById(R.id.secondphone_register_btn_ctfnumber); //인증번호전송 버튼
        mBtnCheck = findViewById(R.id.secondphone_register_btn_certconfirm); //인증번호 확인 버튼
        mBtnRegister = findViewById(R.id.secondphone_register_btn); //추가하기 버튼
        mIvBack = findViewById(R.id.secondphone_register_iv_backbtn); //뒤로가기
        mIvExit = findViewById(R.id.secondphone_register_iv_crossbtn); //x버튼
        mTvCtfNumber = findViewById(R.id.secondphone_register_tv_failure_ctfnumber); //인증번호 fail guide
        mLlCertNum = findViewById(R.id.secondphone_register_ll_certnumber); //인증번호 확인 Linear Layout
        timerView = findViewById(R.id.secondphone_register_tv_timer); //인증번호 타이머
        mBtnSend.setOnClickListener(new View.OnClickListener(){ //인증번호 전송 버튼
            @Override
            public void onClick(View v) {
                certPhone();
                showCustomToast("인증번호가 전송되었습니다.");
                mLlCertNum.setVisibility(View.VISIBLE);
                timerView.start(300000);
                if(!timerView.isCertification()){
                    timerView.setText("인증시간초과");
                }
            }
        });

        mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(mStCertNum.equals(mEtCtfNumber.getText().toString())){
                    //인증번호가 일치함
                    mTvCtfNumber.setText(R.string.certnumber_guide);
                    mTvCtfNumber.setTextColor(R.color.colorPrimary);
                    mTvCtfNumber.setVisibility(View.VISIBLE);
                    timerView.setVisibility(View.GONE);
                    REGISTER_POSSIBLE = true;
                    //키보드 내림
                    InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }else{
                    //인증번호가 일치하지 않음
                    mTvCtfNumber.setText(R.string.certnumber_fail_guide);
                    mTvCtfNumber.setVisibility(View.VISIBLE);
                    REGISTER_POSSIBLE = false;
                }
            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIvCtfNumRemoveBtn = findViewById(R.id.secondphone_register_iv_ctfnumber_remove);
        mEtCtfNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvCtfNumRemoveBtn.getVisibility()==View.GONE){
                    mIvCtfNumRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvCtfNumRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvCtfNumRemoveBtn.setVisibility(View.GONE);
                }
            }
        });
        mIvCtfNumRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtCtfNumber.setText("");
                REGISTER_POSSIBLE = false;
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(REGISTER_POSSIBLE==true) {
                    registerSecondPhone();
                } else{
                    showCustomToast("휴대폰 인증을 진행해주세요.");
                }
            }
        });
    }

    private String setPhoneNumber(String fir, String mid, String fin){
        mStPhone = fir + mid + fin;
        return mStPhone;
    }

    private String setCertNumber(){
        SendSMS sendSMS = new SendSMS();
        mStCertNum = sendSMS.numberGen(4, 2);
        return mStCertNum;
    }

    public void registerSecondPhone(){
        setPhoneNumber(mStPhone_fir,mEtPhoneMid.getText().toString(), mEtPhoneFin.getText().toString());

        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        SetSecondPhoneRequest setSecondPhoneRequest = new SetSecondPhoneRequest();
        setSecondPhoneRequest.setSecond_phone(mStPhone);
        secondPhoneService.setSecondPhone(setSecondPhoneRequest);

    }

    private void certPhone(){
        setPhoneNumber(mStPhone_fir, mEtPhoneMid.getText().toString(), mEtPhoneFin.getText().toString());

        setCertNumber();

        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        CertPhoneRequest certPhoneRequest = new CertPhoneRequest();
        certPhoneRequest.setPhone(mStPhone);
        certPhoneRequest.setCert(mStCertNum);
        secondPhoneService.postCertPhone(certPhoneRequest);
    }


    @Override
    public void setSecondPhoneSuccess(SetSecondPhoneResponse setSecondPhoneResponse, ErrorResponse errorResponse) {
        if(setSecondPhoneResponse != null){
            if(setSecondPhoneResponse.getCode() == 200 && setSecondPhoneResponse.isSuccess()){
                showCustomToast("해당 2차전화번호가 추가되었습니다.");
                ((MainActivity)MainActivity.mContext).callGetUser();
                Intent intent = new Intent(SecondPhoneRegisterActivity.this, SecondPhoneActivity.class);
                finish();
                startActivity(intent);
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode()==-601){
                showCustomToast("2차 전화번호는 최대 5개까지 등록 가능합니다.");
            }
        }
    }

    @Override
    public void setSecondPhoneFailure() {

    }

    @Override
    public void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse) {

    }

    @Override
    public void postCertPhoneFailure() {

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