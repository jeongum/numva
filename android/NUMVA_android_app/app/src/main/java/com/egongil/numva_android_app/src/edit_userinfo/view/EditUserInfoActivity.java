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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.ActivityEditUserInfoBinding;
import com.egongil.numva_android_app.src.cert_phone.view.PassActivity;
import com.egongil.numva_android_app.src.config.models.UserInfo;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.request.EditUserInfoRequest;
import com.egongil.numva_android_app.src.config.models.response.EditUserInfoResponse;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.EditUserInfoActivityContract;
import com.egongil.numva_android_app.src.edit_userinfo.models.EditUserInfoService;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

import java.util.Random;

public class EditUserInfoActivity extends BaseActivity implements EditUserInfoActivityContract, ConnectionReceiver.ConnectionReceiverListener {
    private UserInfo mUserInfo;
    static final int PASS_EDITUSERINFO_CODE = 999;

    ActivityEditUserInfoBinding binding;
    ActivityResultLauncher<Intent> mActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_info);

        checkConnection(); //네트워크 확인

        binding.edituserinfoIvCrossbtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                putIntentUserInfo();
                finish();
            }
        });

        //Intent에서 UserData 받아오기
        initializeInfo();

        //생년월일
        binding.edituserinfoEtBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ImageView mIvBirthRemoveBtn = binding.edituserinfoIvBirthRemove;
                if (editable.length() > 0 && mIvBirthRemoveBtn.getVisibility() == View.GONE) {
                    mIvBirthRemoveBtn.setVisibility(View.VISIBLE);
                } else if (editable.length() == 0 && mIvBirthRemoveBtn.getVisibility() == View.VISIBLE) {
                    mIvBirthRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

        //닉네임
        binding.edituserinfoEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ImageView mIvNicknameRemoveBtn = binding.edituserinfoIvNicknameRemove;
                if (editable.length() > 0 && mIvNicknameRemoveBtn.getVisibility() == View.GONE) {
                    mIvNicknameRemoveBtn.setVisibility(View.VISIBLE);
                } else if (editable.length() == 0 && mIvNicknameRemoveBtn.getVisibility() == View.VISIBLE) {
                    mIvNicknameRemoveBtn.setVisibility(View.GONE);
                }
            }
        });

        binding.edituserinfoIvBirthRemove.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                binding.edituserinfoEtBirth.setText("");
            }
        });

        binding.edituserinfoIvNicknameRemove.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                binding.edituserinfoEtNickname.setText("");
            }
        });

        //PASS버튼
        binding.edituserinfoBtnCtfnumber.setOnClickListener(v -> {
            Intent intent = new Intent(EditUserInfoActivity.this, PassActivity.class);
            intent.putExtra("request_page", "edituserinfo");
            mActivityResultLauncher.launch(intent);
        });

        binding.edituserinfoBtnRandomnickname.setOnClickListener(v -> binding.edituserinfoEtNickname.setText(setRandNick()));

        binding.edituserinfoTvDeleteAccount.setOnClickListener(new OnSingleClickListener() {
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

        binding.edituserinfoBtnSave.setOnClickListener(new OnSingleClickListener() {
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

                mUserInfo.setName(strName);
                mUserInfo.setPhone(strPhone);
                mUserInfo.setBirth(strBirth);

                setUserData();
            }
        });
    }

    private void initializeInfo() {
        Intent intent = getIntent();
        mUserInfo = (UserInfo) intent.getSerializableExtra("user_info");
        setUserData();
    }

    private void setUserData() {
        binding.edituserinfoTvUsername.setText(mUserInfo.getName());
        binding.edituserinfoTvUseremail.setText(mUserInfo.getEmail());
        binding.edituserinfoEtBirth.setText(mUserInfo.getBirth());
        binding.edituserinfoEtNickname.setText(mUserInfo.getNickname());
        binding.edituserinfoEtPhone.setText(mUserInfo.getPhone());
    }

    @Override
    public void postEditUserInfoSuccess(EditUserInfoResponse editUserInfoResponse, ErrorResponse errorResponse) {
        if (editUserInfoResponse != null) {
            if (editUserInfoResponse.getCode() == 200 && editUserInfoResponse.isSuccess()) {
                showCustomToast("회원정보 수정 완료되었습니다.");
                putIntentUserInfo();//finish_intent에 userData 담기
                finish();
            }
        } else if (errorResponse != null) {
        }
    }

    @Override
    public void postEditUserInfoFailure() {

    }

    private void editUserInfo() {
        mUserInfo.setNickname(binding.edituserinfoEtNickname.getText().toString());
        mUserInfo.setBirth(binding.edituserinfoEtBirth.getText().toString());

        EditUserInfoService editUserInfoService = new EditUserInfoService(this);
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        editUserInfoRequest.setPhone(mUserInfo.getPhone());
        editUserInfoRequest.setBirth(mUserInfo.getBirth());
        editUserInfoRequest.setNickname(binding.edituserinfoEtNickname.getText().toString());

        editUserInfoService.postEditUserInfo(editUserInfoRequest);
    }

    //랜덤닉네임 메소드
    private String setRandNick() {
        Random rand = new Random();
        int n, m;
        String strnick1, strnick2;

        String[] nick1 = getResources().getStringArray(R.array.nick1);
        n = rand.nextInt(nick1.length - 1);
        strnick1 = nick1[n];

        String[] nick2 = getResources().getStringArray(R.array.nick2);
        m = rand.nextInt(nick2.length - 1);
        strnick2 = nick2[m];

        return strnick1 + strnick2;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        } else {

        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if (!isConnected) {
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }

    //MyPageFragment 향할 intent에 userData 담기
    public void putIntentUserInfo() {
        Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class);
        finish_intent.putExtra("user_info", mUserInfo);
        setResult(EDIT_USERINFO_ACTIVITY, finish_intent);
    }
}