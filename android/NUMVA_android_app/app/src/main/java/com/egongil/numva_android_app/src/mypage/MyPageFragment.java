package com.egongil.numva_android_app.src.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.app_info.AppInfoActivity;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.GlobalAuthHelper;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.customer_center.CustomerCenterActivity;
import com.egongil.numva_android_app.src.edit_userinfo.EditUserInfoActivity;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageFragmentView;
import com.egongil.numva_android_app.src.mypage.models.LogoutResponse;
import com.egongil.numva_android_app.src.notification_setting.NotiSettingActivity;
import com.egongil.numva_android_app.src.qr_management.QrManagementActivity;
import com.egongil.numva_android_app.src.second_phone.SecondPhoneActivity;

import java.util.Locale;

import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

public class MyPageFragment extends BaseFragment implements MyPageFragmentView {
    TextView mTvUserName, mTvUserEmail, mTvUserPhone, mTvUserSecPhone, mTvPhone;
    LinearLayout mLlNonLoginGreeting, mLlLoginGreeting, mLlLoginPhoneInfo;
    View mNonLoginBoundaryLine;
    CustomViewMyPageListItem mCvRegisterQrNum, mCvEditUserInfo, mCvNotiSetting, mCvCustomerService, mCvAppInfo, mCvLogout;
    ImageView mIvEditPhone, mIvEditSecondPhone;
    ConstraintLayout mBtnSecPhoneRegister;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public Callback mGetPhoneInfoCallback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        mLlNonLoginGreeting = view.findViewById(R.id.mypage_ll_nonlogin_greeting);
        mNonLoginBoundaryLine = view.findViewById(R.id.mypage_v_nonlogin_boundary_line);
        mLlLoginGreeting = view.findViewById(R.id.mypage_ll_login_greeting);
        mLlLoginPhoneInfo = view.findViewById(R.id.mypage_ll_login_phone_info);
        mTvUserName = view.findViewById(R.id.mypage_tv_username);
        mTvUserEmail = view.findViewById(R.id.mypage_tv_useremail);
        mTvUserPhone = view.findViewById(R.id.mypage_tv_phone_fir);
        mTvUserSecPhone = view.findViewById(R.id.mypage_tv_phone_sec);
        mTvPhone = view.findViewById(R.id.mypage_tv_phone);
        mIvEditPhone = view.findViewById(R.id.mypage_iv_phone_edit);
        mIvEditSecondPhone = view.findViewById(R.id.mypage_iv_secondphone_edit);
        mBtnSecPhoneRegister = view.findViewById(R.id.mypage_cl_secondphone_register);

        mCvRegisterQrNum = view.findViewById(R.id.mypage_cv_register_qr_number);
        mCvEditUserInfo = view.findViewById(R.id.mypage_cv_edit_user_info);
        mCvNotiSetting = view.findViewById(R.id.mypage_cv_noti_setting);
        mCvCustomerService = view.findViewById(R.id.mypage_cv_customer_service);
        mCvAppInfo = view.findViewById(R.id.mypage_cv_app_info);
        mCvLogout = view.findViewById(R.id.mypage_cv_logout);

        mSwipeRefreshLayout = view.findViewById(R.id.mypage_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary
        );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")!=""){
                    //비로그인상태가 아닐 경우
                    ((MainActivity)getActivity()).callGetUser();
                }

                // 업데이트가 끝났음을 알림
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        setInitialLoginState();

        //유저정보 클릭 시 > 내 정보 수정 진입
        mLlLoginGreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        //휴대전화번호 텍뷰 클릭 시 > 내 정보 수정 진입
        mTvPhone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        //휴대전화번호 클릭 시 > 내 정보 수정 진입
        mTvUserPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        //휴대전화번호 옆 pencil 아이콘 클릭 시 > 내 정보 수정 진입
        mIvEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        //2차전화번호 옆 pencil 아이콘 클릭 시 > 2차전화번호 다이얼로그 진입
        mIvEditSecondPhone.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);
            }
        });

        //2차전화번호 클릭 시 > 2차전화번호 다이얼로그 진입
        mTvUserSecPhone.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);

//                SecondPhoneDialog secondPhoneDialog = new SecondPhoneDialog(getContext());
//                secondPhoneDialog.showSecondPhoneDialog();
            }
        });

        //등록 버튼 클릭 시 > 2차전화번호 다이얼로그 진입
        mBtnSecPhoneRegister.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);
            }
        });

        mLlNonLoginGreeting.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //내 QR전화번호판
        mCvRegisterQrNum.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), QrManagementActivity.class);
                startActivity(intent);
            }
        });

        //내 정보 수정
        mCvEditUserInfo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(intent);
            }
        });

        //푸시알림 설정
        mCvNotiSetting.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), NotiSettingActivity.class);
                startActivity(intent);
            }
        });

        //고객센터
        mCvCustomerService.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerCenterActivity.class);
                startActivity(intent);
            }
        });

        //앱 정보 확인
        mCvAppInfo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), AppInfoActivity.class);
                startActivity(intent);
            }
        });

        mCvLogout.setOnClickListener(new OnSingleClickListener() {
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
                confirmLogoutDialog.mBtnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmLogoutDialog.dismiss();
                        logout();
                        setNonLoginState();
                    }
                });
            }
        });

        mGetPhoneInfoCallback = new Callback() {
            @Override
            public void callback() {
                setLoginState();
            }
        };

        setHasOptionsMenu(true);

        return view;
    }
    public void setInitialLoginState(){
        if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")=="") {
            setNonLoginState();
        }else{
            setLoginState();
        }
    }

    public void setNonLoginState(){
        mLlNonLoginGreeting.setVisibility(View.VISIBLE);
        mNonLoginBoundaryLine.setVisibility(View.VISIBLE);
        mLlLoginGreeting.setVisibility(View.GONE);
        mLlLoginPhoneInfo.setVisibility(View.GONE);
        mCvLogout.setVisibility(View.GONE);
        mCvRegisterQrNum.setVisibility(View.GONE);
        mCvEditUserInfo.setVisibility(View.GONE);
        mCvNotiSetting.setVisibility(View.GONE);
    }
    public void setLoginState(){
        mLlNonLoginGreeting.setVisibility(View.GONE);
        mNonLoginBoundaryLine.setVisibility(View.GONE);
        mLlLoginGreeting.setVisibility(View.VISIBLE);
        mLlLoginPhoneInfo.setVisibility(View.VISIBLE);
        mCvRegisterQrNum.setVisibility(View.VISIBLE);
        mCvEditUserInfo.setVisibility(View.VISIBLE);
        mCvNotiSetting.setVisibility(View.VISIBLE);

        //data mapping
        if(((MainActivity)getActivity()).userInfo!=null){
            mTvUserName.setText(((MainActivity)getActivity()).userInfo.getNickname());
            mTvUserEmail.setText(((MainActivity)getActivity()).userInfo.getEmail());

            String strUserPhone = ((MainActivity)getActivity()).userInfo.getPhone();
            String strUserSecPhone = ((MainActivity)getActivity()).userInfo.getSecond_phone();
            mTvUserPhone.setText((PhoneNumberUtils.formatNumber(strUserPhone, Locale.getDefault().getCountry())));
            if(strUserSecPhone != null){
                mTvUserSecPhone.setText((PhoneNumberUtils.formatNumber(strUserSecPhone, Locale.getDefault().getCountry())));
                mTvUserSecPhone.setVisibility(View.VISIBLE);  //2차전화번호 TextView
                mIvEditSecondPhone.setVisibility(View.VISIBLE); //2차전화번호 연필아이콘
                mBtnSecPhoneRegister.setVisibility(View.GONE);  //2차전화번호 등록버튼
            }
            else if(strUserSecPhone == null){
                mTvUserSecPhone.setVisibility(View.GONE);  //2차전화번호 TextView
                mIvEditSecondPhone.setVisibility(View.GONE);  //2차전화번호 연필아이콘
                mBtnSecPhoneRegister.setVisibility(View.VISIBLE);  //2차전화번호 등록버튼
            }
        }
    }

    public void logout(){
        MyPageService myPageService = new MyPageService(this);
        myPageService.getLogout();

        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

//    public void getPhoneInfo(Callback mCallback){
//        SecondPhoneService secondPhoneService = new SecondPhoneService();
//
//    }

    @Override
    public void getLogoutSuccess(LogoutResponse logoutResponse, ErrorResponse errorResponse) {
        //모든 에러에 대해 성공한 경우와 동일 처리하므로 에러코드 분기 없음(토큰 초기화 후 로그인 페이지로 돌아감)
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(X_ACCESS_TOKEN, null);
        editor.commit();

        System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));
        ((MainActivity)MainActivity.mContext).accountLogout();

        showCustomToast("정상적으로 로그아웃되었습니다.");

//        if(logoutResponse!=null){
//            if(logoutResponse.getCode()==200 && logoutResponse.isSuccess()){
//                SharedPreferences.Editor editor = sSharedPreferences.edit();
//                editor.putString(X_ACCESS_TOKEN, null);
//                editor.commit();
//
//                System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));
//
//                showCustomToast("정상적으로 로그아웃되었습니다.");
//            }
//        }
//        else if(errorResponse != null){
//
//        }
    }

    @Override
    public void getLogoutFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }


}