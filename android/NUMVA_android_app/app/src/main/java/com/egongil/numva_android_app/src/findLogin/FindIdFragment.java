package com.egongil.numva_android_app.src.findLogin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.egongil.numva_android_app.src.config.view.BaseFragment;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.interfaces.FindIdActivityView;
import com.egongil.numva_android_app.src.config.models.request.FindIdRequest;
import com.egongil.numva_android_app.src.config.models.response.FindIdResponse;

public class FindIdFragment extends BaseFragment implements FindIdActivityView {

    EditText mEtName, mEtCtfNumber, mEtPhoneMid, mEtPhoneFin;
    Button mBtnCertSend, mBtnCertConfirm, mBtnFindId;
    ImageView mIvNameRemoveBtn,mIvCtfNumberRemoveBtn;
    Spinner mSpPhoneFir;
    String mStPhone, mStPhone_fir, mStCertNum;
    TextView mTvCtfNumber, mTvCrtNumberTimer;
    LinearLayout mLlCertNum;
    static Boolean FINDID_POSSIBLE = false;

    private TimerView timerView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_find_id, container, false);


        mEtName = view.findViewById(R.id.findid_et_name);  //이름
        mSpPhoneFir = view.findViewById(R.id.findid_sp_phone_start);  //전화번호 첫 3자리 spinner
        mEtPhoneMid = view.findViewById(R.id.findid_et_phone_middle);  //중간 전화번호
        mEtPhoneFin = view.findViewById(R.id.findid_et_phone_final);  //끝 전화번호
        mEtCtfNumber = view.findViewById(R.id.findid_et_certnumber);  //인증번호

        mBtnCertSend = view.findViewById(R.id.findid_btn_ctfnumber);  //인증번호 전송 버튼
        mBtnCertConfirm = view.findViewById(R.id.findid_btn_certconfirm);  //인증번호 확인 버튼
        mBtnFindId = view.findViewById(R.id.findid_btn);  //아이디 찾기 버튼

        mIvNameRemoveBtn = view.findViewById(R.id.findid_Iv_name_remove); //이름 초기화
        mIvCtfNumberRemoveBtn = view.findViewById(R.id.findid_Iv_ctfnumber_remove); //인증번호 초기화


        mTvCtfNumber = view.findViewById(R.id.findid_tv_failure_ctfnumber); //인증번호 fail guide

        mLlCertNum = view.findViewById(R.id.findid_ll_certnumber); //인증번호 확인 Linear Layout

        timerView = view.findViewById(R.id.findid_tv_timer);
        mBtnCertSend.setOnClickListener(new View.OnClickListener(){ //인증번호 전송 버튼
            @Override
            public void onClick(View v) {
                certPhone();
                mLlCertNum.setVisibility(View.VISIBLE);
                timerView.start(300000);
                if(timerView.isCertification()){
                    //TODO : 인증과정
                }
                else{
                    timerView.setText("인증시간초과");
                }
            }
        });

        mBtnCertConfirm.setOnClickListener(new View.OnClickListener() { //인증번호 확인 버튼
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(mStCertNum.equals(mEtCtfNumber.getText().toString())){
                    mTvCtfNumber.setText(getString(R.string.certnumber_guide));
                    mTvCtfNumber.setTextColor(R.color.colorPrimary);
                    mTvCtfNumber.setVisibility(View.VISIBLE);
                    timerView.setVisibility(View.GONE);
                    FINDID_POSSIBLE = true; //FindId 가능한 상태
                }
                else{
                    mTvCtfNumber.setText(R.string.certnumber_fail_guide);
                    mTvCtfNumber.setVisibility(View.VISIBLE);
                    FINDID_POSSIBLE = false; //FindId 불가능한 상태
                }
            }
        });

        //Spinner 연결
        String[] str = this.getResources().getStringArray(R.array.phone_dropdown);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_phonenumber, str);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        mSpPhoneFir.setAdapter(adapter);
        mSpPhoneFir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStPhone_fir = (mSpPhoneFir.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mBtnFindId.setOnClickListener(new View.OnClickListener() { //ID찾기 버튼
            @Override
            public void onClick(View v) {
                checkFindIdAvailable();
                if(FINDID_POSSIBLE) {
                    findId();
                }else{
                    showCustomToast("모든 항목을 입력해주세요.");
                }
            }
        });

        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvNameRemoveBtn.getVisibility()==View.GONE){
                    mIvNameRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvNameRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvNameRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

        mEtCtfNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0 && mIvCtfNumberRemoveBtn.getVisibility()==View.GONE){
                    mIvCtfNumberRemoveBtn.setVisibility(View.VISIBLE);
                }else if(s.length()==0 && mIvCtfNumberRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvCtfNumberRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

        mIvNameRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtName.setText("");
            }
        });

        mIvCtfNumberRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtCtfNumber.setText("");
                FINDID_POSSIBLE = false; //FindId 불가능한 상태
            }
        });


         return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    //ID찾기 api 성공
    @Override
    public void postFindIdSuccess(FindIdResponse findIdResponse, ErrorResponse errorResponse) {
        if(findIdResponse != null){
            if(findIdResponse.getCode()==200 && findIdResponse.isSuccess()){
                //해당 이름, 번호로 가입한 email 있을 때
                FindIdExistFragment findIdExistFragment = new FindIdExistFragment();
                Bundle bundle = new Bundle(1);
                bundle.putString("Email", findIdResponse.getResult());
                findIdExistFragment.setArguments(bundle);
                ((FindLoginActivity)getActivity()).replaceFragment(findIdExistFragment);

            }
        }
        else if(errorResponse!=null){
            showCustomToast(errorResponse.getCode()+"");
            //해당 이름, 번호로 가입한 email 없을 때
            if(errorResponse.getCode()==-103){
                FindIdNonExistFragment findIdNonExistFragment = new FindIdNonExistFragment();
                ((FindLoginActivity)getActivity()).replaceFragment(findIdNonExistFragment);
            } else if(FINDID_POSSIBLE.equals(false)){
                showCustomToast("휴대폰 번호를 인증하세요.");
            }
        }

    }

    //ID찾기 api 실패
    @Override
    public void postFindIdFailure() {

    }


    //문자인증 api 성공
    @Override
    public void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse) {
        if(certPhoneResponse != null){
            if(certPhoneResponse.getCode()==200 && certPhoneResponse.isSuccess()){
                //문자보냄
                Toast.makeText(getContext(), "인증번호 전송 완료", Toast.LENGTH_SHORT).show();
            }
        }
        else if(errorResponse != null){
            showCustomToast(errorResponse.getCode()+"");
        }

    }


    //문자인증 api 실패
    @Override
    public void postCertPhoneFailure() {

    }

    private void findId(){
        setPhoneNumber(mStPhone_fir, mEtPhoneMid.getText().toString(), mEtPhoneFin.getText().toString());

        FindLoginService findLoginService = new FindLoginService(this);
        FindIdRequest findIdRequest = new FindIdRequest();
        findIdRequest.setName(mEtName.getText().toString());
        findIdRequest.setPhone(mStPhone);
        findLoginService.postFindId(findIdRequest);
    }

    private void certPhone(){
        setPhoneNumber(mStPhone_fir, mEtPhoneMid.getText().toString(), mEtPhoneFin.getText().toString());

        setCertNumber();
//        SendSMS sendSMS = new SendSMS();
//        mStCertNum = sendSMS.numberGen(4, 2);

        FindLoginService findLoginService = new FindLoginService(this);
        CertPhoneRequest certPhoneRequest = new CertPhoneRequest();
        certPhoneRequest.setPhone(mStPhone);
        certPhoneRequest.setCert(mStCertNum);
        findLoginService.postCertPhone(certPhoneRequest);
    }

    //전체 핸드폰번호 string으로 붙이는 메소드 : spinner + editText
    private String setPhoneNumber(String fir, String mid, String fin){
        mStPhone = fir + mid + fin;
        return mStPhone;
    }

    private String setCertNumber(){
        SendSMS sendSMS = new SendSMS();
        mStCertNum = sendSMS.numberGen(4, 2);
        return mStCertNum;
    }

    private void checkFindIdAvailable(){
        if(mEtName.getText().length()==0 || mEtCtfNumber.getText().length()==0){
            FINDID_POSSIBLE = false;
        }
    }




}