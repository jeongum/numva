package com.egongil.numva_android_app.src.mypage.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.app_info.AppInfoActivity;
import com.egongil.numva_android_app.src.config.models.SafetyInfo;
import com.egongil.numva_android_app.src.config.view.BaseFragment;
import com.egongil.numva_android_app.src.config.interfaces.Callback;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.LogoutResponse;
import com.egongil.numva_android_app.src.config.models.UserInfo;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.customer_center.view.CustomerCenterActivity;
import com.egongil.numva_android_app.src.edit_userinfo.view.EditUserInfoActivity;
import com.egongil.numva_android_app.src.login.view.LoginActivity;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.main.viewmodels.MainViewModel;
import com.egongil.numva_android_app.src.mypage.model.MyPageService;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageFragmentContract;
import com.egongil.numva_android_app.src.notification_setting.NotiSettingActivity;
import com.egongil.numva_android_app.src.qr_management.view.QrManagementActivity;
import com.egongil.numva_android_app.src.second_phone.SecondPhoneActivity;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.EDIT_USERINFO_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.QR_MANAGEMENT_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

import java.util.ArrayList;

public class MyPageFragment extends BaseFragment implements MyPageFragmentContract {
    FragmentMypageBinding binding;
    MainViewModel mMainViewModel;

    public Callback mGetPhoneInfoCallback;

    ActivityResultLauncher<Intent> mActivityResultLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false);
        View root = binding.getRoot();

        //MainActivity의 ViewModel 가져옴
        mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.setViewModel(mMainViewModel);
        binding.setLifecycleOwner(this);

        binding.refreshLayout.setColorSchemeResources(
                R.color.colorPrimary
        );
        binding.refreshLayout.setOnRefreshListener(() -> {
            if (((MainActivity) getActivity()).isLogin()) {
                //로그인상태일 경우
                ((MainActivity) getActivity()).getUser();
            }

            // 업데이트가 끝났음을 알림
            binding.refreshLayout.setRefreshing(false);
        });

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            switch (result.getResultCode()) {
                case QR_MANAGEMENT_ACTIVITY:
                    Intent qrIntent = result.getData();
                    ArrayList<SafetyInfo> mListQR = (ArrayList<SafetyInfo>) qrIntent.getSerializableExtra("safety_info");
                    mMainViewModel.setSafetyInfoData(mListQR);
                    break;

                case EDIT_USERINFO_ACTIVITY:
                    Intent editIntent = result.getData();
                    UserInfo info = (UserInfo)editIntent.getSerializableExtra("user_info");
                    mMainViewModel.setUserData(info);
                    break;
            }
        });

        //유저정보 클릭 시 > 내 정보 수정 진입
        binding.loginGreeting.setOnClickListener(v -> {
            launchEditUserInfoActivity();
        });

        //휴대전화번호 텍뷰 클릭 시 > 내 정보 수정 진입
        binding.phoneNumberTitle.setOnClickListener(v -> {
            launchEditUserInfoActivity();
        });

        //휴대전화번호 클릭 시 > 내 정보 수정 진입
        binding.phoneNumberTv.setOnClickListener(v -> {
            launchEditUserInfoActivity();
        });

        //휴대전화번호 옆 pencil 아이콘 클릭 시 > 내 정보 수정 진입
        binding.editPhoneBtn.setOnClickListener(v -> {
            launchEditUserInfoActivity();
        });

        //2차전화번호 옆 pencil 아이콘 클릭 시 > 2차전화번호 다이얼로그 진입
        binding.editSecondPhoneBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);
            }
        });

        //2차전화번호 클릭 시 > 2차전화번호 다이얼로그 진입
        binding.secondPhoneTv.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);
            }
        });

        //등록 버튼 클릭 시 > 2차전화번호 다이얼로그 진입
        binding.registerSecondphone.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);
            }
        });

        binding.nonLoginGreeting.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //내 QR전화번호판
        binding.registerQrBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), QrManagementActivity.class);
                intent.putExtra("safety_info", mMainViewModel.getSafetyInfoData().getValue());  //safetyInfo 정보 담아서 보낸다.
                mActivityResultLauncher.launch(intent);
            }
        });

        //내 정보 수정
        binding.editUserInfoBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                launchEditUserInfoActivity();
            }
        });

        //푸시알림 설정
        binding.notiSettingBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), NotiSettingActivity.class);
                startActivity(intent);
            }
        });

        //고객센터
        binding.customerServiceBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerCenterActivity.class);
                startActivity(intent);
            }
        });

        //앱 정보 확인
        binding.appInfoBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), AppInfoActivity.class);
                startActivity(intent);
            }
        });

        binding.logoutBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                TwoButtonDialog confirmLogoutDialog = new TwoButtonDialog(getActivity());
                confirmLogoutDialog.showDialog();
                confirmLogoutDialog.setContextText("로그아웃하시겠습니까?");
                confirmLogoutDialog.mBtnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmLogoutDialog.dismiss();
                    }
                });
                confirmLogoutDialog.mBtnRight.setOnClickListener(v1 -> {
                    confirmLogoutDialog.dismiss();
                    logout();
                });
            }
        });

        mGetPhoneInfoCallback = () -> setSecondPhoneData();

        setHasOptionsMenu(true);

        return root;
    }

    public void setSecondPhoneData() {
        //data mapping
        if (((MainActivity) getActivity()).userInfo != null) {
            if (mMainViewModel.getUserData().getValue().getSecond_phone().equals("")) {
                binding.secondPhoneTv.setVisibility(View.VISIBLE);  //2차전화번호 TextView
                binding.editSecondPhoneBtn.setVisibility(View.VISIBLE); //2차전화번호 연필아이콘
                binding.registerSecondphone.setVisibility(View.GONE);  //2차전화번호 등록버튼
            } else {
                binding.secondPhoneTv.setVisibility(View.GONE);  //2차전화번호 TextView
                binding.editSecondPhoneBtn.setVisibility(View.GONE);  //2차전화번호 연필아이콘
                binding.registerSecondphone.setVisibility(View.VISIBLE);  //2차전화번호 등록버튼
            }
        }
    }

    public void logout() {
        MyPageService myPageService = new MyPageService(this);
        myPageService.getLogout();

        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void getLogoutSuccess(LogoutResponse logoutResponse, ErrorResponse errorResponse) {
        //모든 에러에 대해 성공한 경우와 동일 처리하므로 에러코드 분기 없음(토큰 초기화 후 로그인 페이지로 돌아감)
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(X_ACCESS_TOKEN, null);
        editor.commit();

        System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));
        ((MainActivity) MainActivity.mContext).accountLogout();

        showCustomToast("정상적으로 로그아웃되었습니다.");
    }

    @Override
    public void getLogoutFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    private void launchEditUserInfoActivity() {
        Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
        intent.putExtra("user_info", mMainViewModel.getUserData().getValue());
        mActivityResultLauncher.launch(intent);
    }
}