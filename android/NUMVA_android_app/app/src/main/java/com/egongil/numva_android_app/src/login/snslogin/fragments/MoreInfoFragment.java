package com.egongil.numva_android_app.src.login.snslogin.fragments;

import static android.view.View.GONE;

import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_BIRTHDAY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_BIRTHYEAR;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_NAME;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_PHONE;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.view.BaseFragment;
import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoFragment extends BaseFragment {
    private EditText mEtName, mEtMobile, mEtBirth, mEtNick;
    private ImageView mIvRemoveBirth, mIvRemoveNick, mIvBack;
    private TextView mTvBirthError, mTvNickError;
    private Button mBtnRegister;

    private List<String> userInfo = new ArrayList<>();

    //동의 항목 체크박스
    private CheckBox mCheckAll, mCheckService, mCheckPrivacy, mCheckMarketing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);

        mCheckAll = view.findViewById(R.id.snslogin_moreinfo_check_all);
        mCheckService = view.findViewById(R.id.snslogin_moreinfo_check_service);
        mCheckPrivacy = view.findViewById(R.id.snslogin_moreinfo_check_privacy);
        mCheckMarketing = view.findViewById(R.id.snslogin_moreinfo_check_marketing);

        mEtName = view.findViewById(R.id.snslogin_moreinfo_et_name);
        mEtMobile = view.findViewById(R.id.snslogin_moreinfo_et_phone);
        mEtBirth = view.findViewById(R.id.snslogin_moreinfo_et_birth);
        mEtNick = view.findViewById(R.id.snslogin_moreinfo_et_nickname);

        mIvBack = view.findViewById(R.id.snslogin_moreinfo_iv_backbtn);
        mIvRemoveBirth = view.findViewById(R.id.snslogin_moreinfo_Iv_birth_remove);
        mIvRemoveNick = view.findViewById(R.id.snslogin_moreinfo_Iv_nickname_remove);
        mTvBirthError = view.findViewById(R.id.snslogin_moreinfo_tv_birth_error);
        mTvNickError = view.findViewById(R.id.snslogin_moreinfo_tv_nickname_error);

        mBtnRegister = view.findViewById(R.id.snslogin_moreinfo_btn_register);

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                //연동 해제
                ((SnsLoginActivity)SnsLoginActivity.mContext).accountResign();
            }
        });
        //수정가능한 EditText removeBtn 처리
        mIvRemoveBirth.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtBirth.setText("");
                mIvRemoveBirth.setVisibility(GONE);
            }
        });
        mIvRemoveNick.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtNick.setText("");
                mIvRemoveNick.setVisibility(GONE);
            }
        });
        mEtBirth.addTextChangedListener(new EditTextWatcher(mEtBirth));
        mEtNick.addTextChangedListener(new EditTextWatcher(mEtNick));
        /////
        //동의 체크박스 처리
        mCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mCheckService.setChecked(true);
                    mCheckPrivacy.setChecked(true);
                    mCheckMarketing.setChecked(true);
                }
            }
        });

        mCheckService.setOnCheckedChangeListener(subCheckListener);
        mCheckPrivacy.setOnCheckedChangeListener(subCheckListener);
        mCheckMarketing.setOnCheckedChangeListener(subCheckListener);
        /////////////

        mBtnRegister.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(checkRegisterAvailable()){
                    int agreement_marketing = (mCheckMarketing.isChecked())? 1 : 0;

                    ((SnsLoginActivity)SnsLoginActivity.mContext).socialRegister(mEtNick.getText().toString(), agreement_marketing);
                }
            }
        });

        initView();

        return view;
    }

    private void initView(){
        //userInfo 가져옴
        ApplicationClass mGlobalHelper = new ApplicationClass();
        userInfo = mGlobalHelper.getGlobalUserLoginInfo();

        mEtName.setText(userInfo.get(USER_NAME));                  //이름
        mEtMobile.setText(userInfo.get(USER_PHONE));                //휴대폰번호
        mEtBirth.setText(userInfo.get(USER_BIRTHYEAR)+userInfo.get(USER_BIRTHDAY)); //생년월일
    }

    private class EditTextWatcher implements TextWatcher {
        private View view;
        private EditTextWatcher(EditText view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int id = view.getId();
            if(id == R.id.snslogin_moreinfo_et_birth) {
                if(s.toString().equals("")){
                    mIvRemoveBirth.setVisibility(View.GONE);
                }else{
                    mIvRemoveBirth.setVisibility(View.VISIBLE);
                }
            }else if(id == R.id.snslogin_moreinfo_et_nickname){
                if(s.toString().equals("")){
                    mIvRemoveNick.setVisibility(View.GONE);
                }else{
                    mIvRemoveNick.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //subcheck항목 동작과 전체동의 연결하는 리스너
    private CompoundButton.OnCheckedChangeListener subCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(mCheckAll.isChecked()){
                mCheckAll.setChecked(false);
            }else if(mCheckService.isChecked() && mCheckPrivacy.isChecked() && mCheckMarketing.isChecked()){
                mCheckAll.setChecked(true);
            }
        }
    };

    //체크박스 회원가입가능상태인지 확인하는 메소드
    private boolean isChecked(CheckBox check1, CheckBox check2, CheckBox check3){
        if(check1.isChecked() || check2.isChecked()&&check3.isChecked()){
            return true;
        }
        return false;
    }

    private boolean checkRegisterAvailable(){
        //동의 check, 닉네임 check, 생일 check
        String nowNick = mEtNick.getText().toString();
        String nowBirth = mEtBirth.getText().toString();
        Boolean registerAvailable = true;
        if(mEtBirth.getText().length()!=8){
            mTvBirthError.setVisibility(View.VISIBLE);
            registerAvailable = false;
        }else{
            mTvBirthError.setVisibility(GONE);
        }

        if(nowNick.equals("") || !nowNick.matches("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$")){
            mTvNickError.setVisibility(View.VISIBLE);
            registerAvailable = false;
        }else{
            mTvNickError.setVisibility(GONE);
        }

        if(!isChecked(mCheckAll, mCheckService, mCheckPrivacy)){
            showCustomToast("필수항목에 동의해주세요!");
            registerAvailable = false;
        }

        if(registerAvailable){
            return true;
        }else{
            return false;
        }
    }
}