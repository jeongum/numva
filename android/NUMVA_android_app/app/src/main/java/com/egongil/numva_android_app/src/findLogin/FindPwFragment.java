package com.egongil.numva_android_app.src.findLogin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.cert_phone.SendSMS;
import com.egongil.numva_android_app.src.cert_phone.TimerView;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneRequest;
import com.egongil.numva_android_app.src.cert_phone.models.CertPhoneResponse;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.interfaces.FindPwActivityView;
import com.egongil.numva_android_app.src.findLogin.models.FindPwRequest;
import com.egongil.numva_android_app.src.findLogin.models.FindPwResponse;

public class FindPwFragment extends BaseFragment implements FindPwActivityView{

    EditText mEtEmail, mEtCtfNumber, mEtPhoneMid, mEtPhoneFin;
    Button mBtnCertSend, mBtnCertConfirm, mBtnFindPw;
    ImageView mIvEmailRemoveBtn, mIvPhoneRemoveBtn, mIvCtfNumberRemoveBtn;
    Spinner mSpPhoneFir;
    String mStPhone, mStPhone_fir, mStCertNum;
    TextView mTvCtfNumber;
    LinearLayout mLlCertNum;
    private TimerView timerView;
    static Boolean FINDPW_POSSIBLE = false;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_find_pw, container, false); //xml????????? view??? ?????????

        mEtEmail = view.findViewById(R.id.findpw_et_email); //?????????
        mSpPhoneFir = view.findViewById(R.id.findpw_sp_phone_start); //???????????? ??? 3?????? spinner
        mEtPhoneMid = view.findViewById(R.id.findpw_et_phone_middle); //?????? ????????????
        mEtPhoneFin = view.findViewById(R.id.findpw_et_phone_final); //??? ????????????
        mEtCtfNumber = view.findViewById(R.id.findpw_et_certnumber);  //????????????

        mBtnCertSend = view.findViewById(R.id.findpw_btn_ctfnumber); //???????????? ?????? ??????
        mBtnCertConfirm = view.findViewById(R.id.findpw_btn_certconfirm); //???????????? ?????? ??????
        mBtnFindPw = view.findViewById(R.id.findpw_btn); //find_pw_reset??????????????? ?????? ??????


        mTvCtfNumber = view.findViewById(R.id.findpw_tv_failure_ctfnumber); //???????????? fail guide

        mLlCertNum = view.findViewById(R.id.findpw_ll_certnum); //???????????? ?????? Linear Layout

        timerView = view.findViewById(R.id.findpw_tv_timer);
        mBtnCertSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                certPhone();
                mLlCertNum.setVisibility(View.VISIBLE);
                timerView.start(300000);
                if(timerView.isCertification()){

                }
                else{
                    timerView.setText("??????????????????");
                }
            }
        });

        mBtnCertConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(mStCertNum.equals(mEtCtfNumber.getText().toString())){
                    mTvCtfNumber.setText(getString(R.string.certnumber_guide));
                    mTvCtfNumber.setTextColor(R.color.colorPrimary);
                    mTvCtfNumber.setVisibility(View.VISIBLE);
                    timerView.setVisibility(View.GONE);
                    FINDPW_POSSIBLE = true;
                }
                else{
                    mTvCtfNumber.setText(getString(R.string.certnumber_fail_guide));
                    mTvCtfNumber.setVisibility(View.VISIBLE);
                    FINDPW_POSSIBLE = false;
                }
            }
        });

        mBtnFindPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkFindPwAvailable();
                if(FINDPW_POSSIBLE){
                     findPw();
                }else{
                    showCustomToast("?????? ????????? ??????????????????.");
                }
            }
        });

        //Spinner ??????
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

        /////////////EditText x?????? ?????????///////////////////
        mIvEmailRemoveBtn = view.findViewById(R.id.findpw_Iv_email_remove);
        mIvCtfNumberRemoveBtn = view.findViewById(R.id.findpw_Iv_ctfnumber_remove);

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
                } else if (s.length() == 0 && mIvCtfNumberRemoveBtn.getVisibility() == View.VISIBLE) {
                    mIvCtfNumberRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

        mIvEmailRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtEmail.setText("");
            }
        });
        mIvCtfNumberRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtCtfNumber.setText("");
                FINDPW_POSSIBLE = false;
            }
        });
        ////////////////////////////////////////////////////////

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void postFindPwSuccess(FindPwResponse findPwResponse, ErrorResponse errorResponse) {
        if(findPwResponse!=null){
            if(findPwResponse.getCode()==200 && findPwResponse.isSuccess()) {

                PwResetFragment pwResetFragment = new PwResetFragment();
                Bundle bundle = new Bundle(2);
                bundle.putString("Email", mEtEmail.getText().toString());
                bundle.putString("Phone", mStPhone);
                pwResetFragment.setArguments(bundle);
                ((FindLoginActivity) getActivity()).replaceFragment(pwResetFragment);  //pw???????????? ????????? ???????????? ????????? ?????????????????? ??????
            }
        }
        else if(errorResponse!=null){
            showCustomToast(errorResponse.getCode()+"");
            if(errorResponse.getCode()==-103){
                FindIdNonExistFragment findIdNonExistFragment = new FindIdNonExistFragment();
                ((FindLoginActivity)getActivity()).replaceFragment(findIdNonExistFragment);

            } else if(FINDPW_POSSIBLE.equals(false)){
                showCustomToast("????????? ????????? ???????????????.");
            }
        }

    }

    @Override
    public void postFindPwFailure() {

    }

    //???????????? api ??????
    @Override
    public void postCertPhoneSuccess(CertPhoneResponse certPhoneResponse, ErrorResponse errorResponse) {
        if(certPhoneResponse != null){
            if(certPhoneResponse.getCode()==200 && certPhoneResponse.isSuccess()){
                //????????????
                showCustomToast("???????????? ?????? ??????");
            }
        }
        else if(errorResponse != null){
            showCustomToast(errorResponse.getCode()+"");
        }
    }

    @Override
    public void postCertPhoneFailure() {

    }


    private void findPw(){
        setPhoneNumber(mStPhone_fir, mEtPhoneMid.getText().toString(), mEtPhoneFin.getText().toString());

        FindLoginService findLoginService = new FindLoginService(this);
        FindPwRequest findPwRequest = new FindPwRequest();
        findPwRequest.setEmail(mEtEmail.getText().toString());
        findPwRequest.setPhone(mStPhone);
        findLoginService.postFindPw(findPwRequest);
    }

    private void certPhone(){
        setPhoneNumber(mStPhone_fir, mEtPhoneMid.getText().toString(), mEtPhoneFin.getText().toString());

        SendSMS sendSMS = new SendSMS();
        mStCertNum = sendSMS.numberGen(4, 2);

        FindLoginService findLoginService = new FindLoginService(this);
        CertPhoneRequest certPhoneRequest = new CertPhoneRequest();
        certPhoneRequest.setPhone(mStPhone);
        certPhoneRequest.setCert(mStCertNum);
        findLoginService.postCertPhone(certPhoneRequest);

    }
    //?????? ??????????????? string?????? ????????? ????????? : spinner + editText
    private String setPhoneNumber(String fir, String mid, String fin){
        mStPhone = fir + mid + fin;
        return mStPhone;
    }

    private void checkFindPwAvailable(){
        if(mEtEmail.getText().length()==0 || mEtCtfNumber.getText().length()==0){
            FINDPW_POSSIBLE = false;
        }
    }


}