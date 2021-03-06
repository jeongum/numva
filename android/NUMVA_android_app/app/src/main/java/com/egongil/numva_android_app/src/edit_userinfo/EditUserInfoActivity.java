package com.egongil.numva_android_app.src.edit_userinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.cert_phone.PassActivity;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.EditUserInfoActivityView;
import com.egongil.numva_android_app.src.edit_userinfo.models.EditUserInfoRequest;
import com.egongil.numva_android_app.src.edit_userinfo.models.EditUserInfoResponse;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

import java.util.Random;

import static android.app.PendingIntent.getActivity;

public class EditUserInfoActivity extends BaseActivity implements EditUserInfoActivityView, ConnectionReceiver.ConnectionReceiverListener {
    Intent intent;
    TextView mTvName, mTvEmail, mTvNickGuide, mTvDeleteAccount, mTvPhone;
    EditText mEtBirth, mEtNickname;
    Button mBtnPass, mBtnRandomNickname, mBtnSave;
    ImageView  mIvBirthRemoveBtn, mIvNicknameRemoveBtn, mIvExit;
    String mStrName, mStrPhone, mStrBirth;
    static final int PASS_EDITUSERINFO_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        checkConnection(); //???????????? ??????

        intent = new Intent();

        mTvName = findViewById(R.id.edituserinfo_tv_username);  //??????
        mTvEmail = findViewById(R.id.edituserinfo_tv_useremail);  //?????????

        mTvPhone = findViewById(R.id.edituserinfo_et_phone); //???????????????
        mEtBirth = findViewById(R.id.edituserinfo_et_birth);  //????????????
        mEtNickname = findViewById(R.id.edituserinfo_et_nickname);  //?????????

        mBtnPass = findViewById(R.id.edituserinfo_btn_ctfnumber);  //pass??????
        mBtnRandomNickname = findViewById(R.id.edituserinfo_btn_randomnickname);  //??????????????? ??????
        mBtnSave = findViewById(R.id.edituserinfo_btn_save);  //?????? ??????

        mIvBirthRemoveBtn = findViewById(R.id.edituserinfo_Iv_birth_remove); //???????????? x
        mIvNicknameRemoveBtn = findViewById(R.id.edituserinfo_Iv_nickname_remove);  //????????? x
        mTvNickGuide = findViewById(R.id.edituserinfo_tv_failure_nickname);  //????????? fail guide

        mTvDeleteAccount = findViewById(R.id.edituserinfo_tv_delete_account); //????????????


        mIvExit = findViewById(R.id.edituserinfo_iv_crossbtn); //x??????
        mIvExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mEtBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0 && mIvBirthRemoveBtn.getVisibility()==View.GONE){
                    mIvBirthRemoveBtn.setVisibility(View.VISIBLE);
                }else if(editable.length()==0 && mIvBirthRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvBirthRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

        mEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0 && mIvNicknameRemoveBtn.getVisibility()==View.GONE){
                    mIvNicknameRemoveBtn.setVisibility(View.VISIBLE);
                }else if(editable.length()==0 && mIvNicknameRemoveBtn.getVisibility()==View.VISIBLE){
                    mIvNicknameRemoveBtn.setVisibility(View.GONE);
                }
            }
        });



        mIvBirthRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtBirth.setText("");
            }
        });

        mIvNicknameRemoveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtNickname.setText("");
            }
        });

        mBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : PASS??????
                Intent intent = new Intent(EditUserInfoActivity.this, PassActivity.class);
                intent.putExtra("request_page", "edituserinfo");
                startActivityForResult(intent,PASS_EDITUSERINFO_CODE);
            }
        });

        mBtnRandomNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtNickname.setText(setRandNick());
            }
        });

        mTvDeleteAccount.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                TwoButtonDialog deleteAccountDialog = new TwoButtonDialog(EditUserInfoActivity.this);
                deleteAccountDialog.showDialog();
                deleteAccountDialog.setContextText("??????????????? ????????????????");
                deleteAccountDialog.setSelectText("??????", "??????");
                deleteAccountDialog.mBtnLeft.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        deleteAccountDialog.dismiss();
                    }
                });
                deleteAccountDialog.mBtnRight.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        Intent intent = new Intent(EditUserInfoActivity.this, DeleteAccountActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        mBtnSave.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                edituserinfo();

                setResult(RESULT_OK, intent);
                finish();
            }
        });



        mStrName = ((MainActivity)MainActivity.mContext).userInfo.getName();
        mStrPhone = ((MainActivity)MainActivity.mContext).userInfo.getPhone();
        mStrBirth = ((MainActivity)MainActivity.mContext).userInfo.getBirth();

        mTvName.setText(mStrName);
        mTvEmail.setText(((MainActivity)MainActivity.mContext).userInfo.getEmail());
        mEtBirth.setText(mStrBirth);
        mEtNickname.setText(((MainActivity)MainActivity.mContext).userInfo.getNickname());
        mTvPhone.setText(mStrPhone);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PASS_EDITUSERINFO_CODE){
            if(resultCode==RESULT_OK){

                mStrName = data.getExtras().getString("name");
                mStrPhone = data.getExtras().getString("phone");
                mStrBirth = data.getExtras().getString("birth");

                mTvName.setText(mStrName);
                mTvPhone.setText(mStrPhone);
                mEtBirth.setText(mStrBirth);
            }
        }
    }

    @Override
    public void postEditUserInfoSuccess(EditUserInfoResponse editUserInfoResponse, ErrorResponse errorResponse) {
        if(editUserInfoResponse!=null){
            if(editUserInfoResponse.getCode()==200 && editUserInfoResponse.isSuccess()){
                showCustomToast("???????????? ?????? ?????????????????????.");
            }
        }
        else if(errorResponse!=null){
        }
    }

    @Override
    public void postEditUserInfoFailure() {

    }

    private void edituserinfo(){

        EditUserInfoService editUserInfoService = new EditUserInfoService(this);
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        editUserInfoRequest.setPhone(mStrPhone);
        editUserInfoRequest.setBirth(mStrBirth);
        editUserInfoRequest.setNickname(mEtNickname.getText().toString());
        editUserInfoService.postEditUserInfo(editUserInfoRequest);

//        ((MainActivity)MainActivity.mContext).callGetUser();

        intent.putExtra("phone", mStrPhone);
        intent.putExtra("birth", mStrBirth);
        intent.putExtra("nickname", mEtNickname.getText().toString());
    }





    //??????????????? ?????????
    private String setRandNick(){
        Random rand = new Random();
        int n, m;
        String strnick1, strnick2;

        String[] nick1 = getResources().getStringArray(R.array.nick1);
        n = rand.nextInt(nick1.length-1);
        strnick1 = nick1[n];

        String[] nick2 = getResources().getStringArray(R.array.nick2);
        m = rand.nextInt(nick2.length-1);
        strnick2 = nick2[m];

        return strnick1+strnick2;
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }else{

        }
    }


    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }
}