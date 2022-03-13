package com.egongil.numva_android_app.src.findLogin;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.findLogin.interfaces.ResetPwActivityView;
import com.egongil.numva_android_app.src.findLogin.models.FindPwResponse;
import com.egongil.numva_android_app.src.findLogin.models.ResetPwRequest;


public class PwResetFragment extends BaseFragment implements ResetPwActivityView {

    EditText mEtPw, mEtCpw;
    Button mBtnPwReset;
    String mEmail, mPhone;
    ImageView mIvPwRemoveBtn, mIvCpwRemoveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_pw, container, false);

        mEtPw = view.findViewById(R.id.resetpw_et_pw);
        mEtCpw = view.findViewById(R.id.resetpw_et_pwc);
        mBtnPwReset = view.findViewById(R.id.resetpw_btn_reset);

        mEmail = getArguments().getString("Email");
        mPhone = getArguments().getString("Phone");

        mBtnPwReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDuplicatePassword(mEtPw.getText().toString(), mEtCpw.getText().toString())){
                    resetPw();
                    PwResetDoneFragment pwResetDoneFragment = new PwResetDoneFragment();
                    ((FindLoginActivity)getActivity()).replaceFragment(pwResetDoneFragment);
                }
            }
        });

        ////////////////////////EditText x버튼 활성화////////////////////////
        mIvPwRemoveBtn = view.findViewById(R.id.resetpw_Iv_pw_remove);
        mIvCpwRemoveBtn = view.findViewById(R.id.resetpw_Iv_cpw_remove);

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
        //////////////////////////////////////////////////////////////////////

        return view;
    }

    @Override
    public void postResetPwSuccess(FindPwResponse findPwResponse, ErrorResponse errorResponse) {
        if(findPwResponse!=null){
            if(findPwResponse.getCode()==200 && findPwResponse.isSuccess()){
                //response 성공했을 때
            }
        }
        else if(errorResponse!=null){
            showCustomToast(errorResponse.getCode()+"");
            if(errorResponse.getCode()==-103){
                //response 실패했을 때
            }
        }
    }

    @Override
    public void postResetPwFailure() {

    }

    private void resetPw(){
        FindLoginService findLoginService = new FindLoginService(this);
        ResetPwRequest resetPwRequest = new ResetPwRequest();
        resetPwRequest.setEmail(mEmail);
        resetPwRequest.setPhone(mPhone);
        resetPwRequest.setNew_pw(mEtPw.getText().toString());
        findLoginService.postResetPw(resetPwRequest);

    }

    public boolean checkDuplicatePassword(String pw, String cpw){
        if(pw.equals(cpw)){
            return true;
        }
        else{
            showCustomToast("비밀번호를 확인하세요");
            return false;
        }
    }
}