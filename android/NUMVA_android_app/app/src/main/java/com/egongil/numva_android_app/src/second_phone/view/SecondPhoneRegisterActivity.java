package com.egongil.numva_android_app.src.second_phone.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.SECONDPHONE_REGISTER_ACTIVITY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.databinding.DataBindingUtil;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.ActivitySecondPhoneRegisterBinding;
import com.egongil.numva_android_app.src.cert_phone.models.SendSMS;
import com.egongil.numva_android_app.src.config.models.request.CertPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.second_phone.models.SecondPhoneService;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneRegisterActivityContract;
import com.egongil.numva_android_app.src.config.models.request.SetSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.SetSecondPhoneResponse;

public class SecondPhoneRegisterActivity extends BaseActivity implements SecondPhoneRegisterActivityContract, ConnectionReceiver.ConnectionReceiverListener {
    String mStrCertNum;  //certNum

    static Boolean REGISTER_POSSIBLE = false;
//    private TimerView timerView;
    private ActivitySecondPhoneRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_phone_register);

        checkConnection(); //네트워크 연결 확인

        binding.sendCertBtn.setOnClickListener(new View.OnClickListener(){ //인증번호 전송 버튼
            @Override
            public void onClick(View v) {
                String phoneNum = makePhoneNumString(binding.etPhoneMiddle.getText().toString(), binding.etPhoneFinal.getText().toString());

                if(phoneNum.equals("")){
                    showCustomToast("올바른 핸드폰 번호를 입력하세요.");
                    return;
                }

                certPhone(phoneNum);
                showCustomToast("인증번호가 전송되었습니다.");
                binding.llCert.setVisibility(View.VISIBLE);
                binding.timer.start(300000);
                if(!binding.timer.isCertification()){
                    binding.timer.setText("인증시간초과");
                }
            }
        });

        binding.btnCertconfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(mStrCertNum.equals(binding.etCertnum.getText().toString())){
                    //인증번호가 일치함
                    binding.tvGuidCert.setText(R.string.certnumber_guide);
                    binding.tvGuidCert.setTextColor(R.color.colorPrimary);
                    binding.tvGuidCert.setVisibility(View.VISIBLE);
                    binding.timer.setVisibility(View.GONE);
                    REGISTER_POSSIBLE = true;
                    //키보드 내림
                    InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }else{
                    //인증번호가 일치하지 않음
                    binding.tvGuidCert.setText(R.string.certnumber_fail_guide);
                    binding.tvGuidCert.setVisibility(View.VISIBLE);
                    REGISTER_POSSIBLE = false;
                }
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.etCertnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && binding.ivCertRemove.getVisibility()==View.GONE){
                    binding.ivCertRemove.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && binding.ivCertRemove.getVisibility()==View.VISIBLE){
                    binding.ivCertRemove.setVisibility(View.GONE);
                }
            }
        });
        binding.ivCertRemove.setOnClickListener(v -> {
            binding.etCertnum.setText("");
            REGISTER_POSSIBLE = false;
        });

        binding.registerBtn.setOnClickListener(v -> {
            if(REGISTER_POSSIBLE==true) {
                registerSecondPhone();
            } else{
                showCustomToast("휴대폰 인증을 진행해주세요.");
            }
        });
    }

    private String makePhoneNumString(String mid, String fin){
        //유효성 검사
        if(mid.length()!=4 || fin.length() != 4)    return "";

        return "010" + mid + fin;
    }

    private String setCertNumber(){
        SendSMS sendSMS = new SendSMS();
        mStrCertNum = sendSMS.numberGen(4, 2);
        return mStrCertNum;
    }

    public void registerSecondPhone(){
        String phoneNum = makePhoneNumString(binding.etPhoneMiddle.getText().toString(), binding.etPhoneFinal.getText().toString());

        if(phoneNum.equals("")){
            showCustomToast("올바른 핸드폰 번호를 입력하세요.");
            return;
        }
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        SetSecondPhoneRequest setSecondPhoneRequest = new SetSecondPhoneRequest();
        setSecondPhoneRequest.setSecond_phone(phoneNum);
        secondPhoneService.setSecondPhone(setSecondPhoneRequest);
    }

    private void certPhone(String phoneNum){
        setCertNumber();

        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        CertPhoneRequest certPhoneRequest = new CertPhoneRequest();
        certPhoneRequest.setPhone(phoneNum);
        certPhoneRequest.setCert(mStrCertNum);
        secondPhoneService.postCertPhone(certPhoneRequest);
    }


    @Override
    public void setSecondPhoneSuccess(SetSecondPhoneResponse setSecondPhoneResponse, ErrorResponse errorResponse) {
        if(setSecondPhoneResponse != null){
            if(setSecondPhoneResponse.getCode() == 200 && setSecondPhoneResponse.isSuccess()){
                showCustomToast("해당 2차전화번호가 추가되었습니다.");

                Intent finish_intent = new Intent(getApplicationContext(), SecondPhoneActivity.class);
                setResult(SECONDPHONE_REGISTER_ACTIVITY, finish_intent);
                finish();
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