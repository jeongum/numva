package com.egongil.numva_android_app.src.edit_userinfo.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.EDIT_USERINFO_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.cert_phone.PassActivity;
import com.egongil.numva_android_app.src.config.models.UserInfo;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.edit_userinfo.model.EditUserInfoService;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.EditUserInfoActivityContract;
import com.egongil.numva_android_app.src.config.models.request.EditUserInfoRequest;
import com.egongil.numva_android_app.src.config.models.response.EditUserInfoResponse;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

import java.util.Random;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class EditUserInfoActivity extends BaseActivity implements EditUserInfoActivityContract, ConnectionReceiver.ConnectionReceiverListener {
    UserInfo mUserInfo;
    TextView mTvName, mTvEmail, mTvNickGuide, mTvDeleteAccount, mTvPhone;
    EditText mEtBirth, mEtNickname;
    Button mBtnPass, mBtnRandomNickname, mBtnSave;
    ImageView  mIvBirthRemoveBtn, mIvNicknameRemoveBtn, mIvExit;

    static final int PASS_EDITUSERINFO_CODE = 999;

    ActivityResultLauncher<Intent> mActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        checkConnection(); //네트워크 확인

        mTvName = findViewById(R.id.edituserinfo_tv_username);  //이름
        mTvEmail = findViewById(R.id.edituserinfo_tv_useremail);  //이메일

        mTvPhone = findViewById(R.id.edituserinfo_et_phone); //핸드폰번호
        mEtBirth = findViewById(R.id.edituserinfo_et_birth);  //생년월일
        mEtNickname = findViewById(R.id.edituserinfo_et_nickname);  //닉네임

        mBtnPass = findViewById(R.id.edituserinfo_btn_ctfnumber);  //pass버튼
        mBtnRandomNickname = findViewById(R.id.edituserinfo_btn_randomnickname);  //랜덤닉네임 버튼
        mBtnSave = findViewById(R.id.edituserinfo_btn_save);  //저장 버튼

        mIvBirthRemoveBtn = findViewById(R.id.edituserinfo_Iv_birth_remove); //생년월일 x
        mIvNicknameRemoveBtn = findViewById(R.id.edituserinfo_Iv_nickname_remove);  //닉네임 x
        mTvNickGuide = findViewById(R.id.edituserinfo_tv_failure_nickname);  //닉네임 fail guide

        mTvDeleteAccount = findViewById(R.id.edituserinfo_tv_delete_account); //회원탈퇴


        mIvExit = findViewById(R.id.edituserinfo_iv_crossbtn); //x버튼
        mIvExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                putIntentUserInfo();
                finish();
            }
        });

        initializeInfo();

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

        mBtnPass.setOnClickListener(v -> {
            Intent intent = new Intent(EditUserInfoActivity.this, PassActivity.class);
            intent.putExtra("request_page", "edituserinfo");
            mActivityResultLauncher.launch(intent);
        });

        mBtnRandomNickname.setOnClickListener(v -> mEtNickname.setText(setRandNick()));

        mTvDeleteAccount.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                TwoButtonDialog deleteAccountDialog = new TwoButtonDialog(EditUserInfoActivity.this);
                deleteAccountDialog.showDialog();
                deleteAccountDialog.setContextText("회원정보를 삭제할까요?");
                deleteAccountDialog.setSelectText("취소", "확인");
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
                editUserInfo();
            }
        });

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == PASS_EDITUSERINFO_CODE) {
                Intent intent = result.getData();

                String strName = intent.getStringExtra("name");
                String strPhone = intent.getStringExtra("phone");
                String strBirth = intent.getStringExtra("birth");

                mTvName.setText(strName);
                mTvPhone.setText(strPhone);
                mEtBirth.setText(strBirth);

                mUserInfo.setName(strName);
                mUserInfo.setPhone(strPhone);
                mUserInfo.setBirth(strBirth);
                //TODO: ViewModel 구현하여 양방향 binding
            }
        });
    }

    private void initializeInfo(){
        Intent intent = getIntent();
        mUserInfo = (UserInfo)intent.getSerializableExtra("user_info");

        //TODO: ViewModel 구현하여 양방향 바인딩
        mTvName.setText(mUserInfo.getName());
        mTvEmail.setText(mUserInfo.getEmail());
        mEtBirth.setText(mUserInfo.getBirth());
        mEtNickname.setText(mUserInfo.getNickname());
        mTvPhone.setText(mUserInfo.getPhone());
    }

    @Override
    public void postEditUserInfoSuccess(EditUserInfoResponse editUserInfoResponse, ErrorResponse errorResponse) {
        if(editUserInfoResponse!=null){
            if(editUserInfoResponse.getCode()==200 && editUserInfoResponse.isSuccess()){
                showCustomToast("회원정보 수정 완료되었습니다.");
                putIntentUserInfo();//finish_intent에 userData 담기
                finish();
            }
        }
        else if(errorResponse!=null){
        }
    }

    @Override
    public void postEditUserInfoFailure() {

    }

    private void editUserInfo(){
        mUserInfo.setNickname(mEtNickname.getText().toString());

        EditUserInfoService editUserInfoService = new EditUserInfoService(this);
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        editUserInfoRequest.setPhone(mUserInfo.getPhone());
        editUserInfoRequest.setBirth(mUserInfo.getBirth());
        editUserInfoRequest.setNickname(mEtNickname.getText().toString());

        editUserInfoService.postEditUserInfo(editUserInfoRequest);
    }
    //랜덤닉네임 메소드
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

    //MyPageFragment 향할 intent에 userData 담기
    public void putIntentUserInfo(){
        Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class);
        finish_intent.putExtra("user_info", mUserInfo);
        setResult(EDIT_USERINFO_ACTIVITY, finish_intent);
    }
}