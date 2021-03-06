package com.egongil.numva_android_app.src.mypage;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.FragmentMypageBinding;
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
import com.egongil.numva_android_app.src.main.models.MainViewModel;
import com.egongil.numva_android_app.src.main.models.UserInfo;
import com.egongil.numva_android_app.src.mypage.interfaces.MyPageFragmentView;
import com.egongil.numva_android_app.src.mypage.models.LogoutResponse;
import com.egongil.numva_android_app.src.notification_setting.NotiSettingActivity;
import com.egongil.numva_android_app.src.qr_management.QrManagementActivity;
import com.egongil.numva_android_app.src.second_phone.SecondPhoneActivity;

import java.util.Locale;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.EDIT_USERINFO_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

public class MyPageFragment extends BaseFragment implements MyPageFragmentView {
    FragmentMypageBinding binding;
    MainViewModel viewModel;

    public Callback mGetPhoneInfoCallback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false);
        View root = binding.getRoot();

        //MainActivity??? ViewModel ?????????
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.refreshLayout.setColorSchemeResources(
                R.color.colorPrimary
        );
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")!=""){
                    //????????????????????? ?????? ??????
                    ((MainActivity)getActivity()).callGetUser();
                }

                // ??????????????? ???????????? ??????
                binding.refreshLayout.setRefreshing(false);
            }
        });

        setInitialLoginState();

        //???????????? ?????? ??? > ??? ?????? ?????? ??????
        binding.loginGreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivityForResult(intent, EDIT_USERINFO_ACTIVITY);
            }
        });

        //?????????????????? ?????? ?????? ??? > ??? ?????? ?????? ??????
        binding.phoneNumberTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivityForResult(intent, EDIT_USERINFO_ACTIVITY);
            }
        });

        //?????????????????? ?????? ??? > ??? ?????? ?????? ??????
        binding.phoneNumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivityForResult(intent, EDIT_USERINFO_ACTIVITY);
            }
        });

        //?????????????????? ??? pencil ????????? ?????? ??? > ??? ?????? ?????? ??????
        binding.editPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivityForResult(intent, EDIT_USERINFO_ACTIVITY);
            }
        });

        //2??????????????? ??? pencil ????????? ?????? ??? > 2??????????????? ??????????????? ??????
        binding.editSecondPhoneBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);
            }
        });

        //2??????????????? ?????? ??? > 2??????????????? ??????????????? ??????
        binding.secondPhoneTv.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), SecondPhoneActivity.class);
                startActivity(intent);

//                SecondPhoneDialog secondPhoneDialog = new SecondPhoneDialog(getContext());
//                secondPhoneDialog.showSecondPhoneDialog();
            }
        });

        //?????? ?????? ?????? ??? > 2??????????????? ??????????????? ??????
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

        //??? QR???????????????
        binding.registerQrBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), QrManagementActivity.class);
                startActivity(intent);
            }
        });

        //??? ?????? ??????
        binding.editUserInfoBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivityForResult(intent, EDIT_USERINFO_ACTIVITY);
            }
        });

        //???????????? ??????
        binding.notiSettingBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), NotiSettingActivity.class);
                startActivity(intent);
            }
        });

        //????????????
        binding.customerServiceBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerCenterActivity.class);
                startActivity(intent);
            }
        });

        //??? ?????? ??????
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
                confirmLogoutDialog.setContextText("???????????????????????????????");
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

        return root;
    }
    public void setInitialLoginState(){
        if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")=="") {
            setNonLoginState();
        }else{
            setLoginState();
        }
    }

    public void setNonLoginState(){
        binding.nonLoginGreeting.setVisibility(View.VISIBLE);
        binding.nonLoginBoundaryLine.setVisibility(View.VISIBLE);
        binding.loginGreeting.setVisibility(View.GONE);
        binding.phoneInfo.setVisibility(View.GONE);
        binding.logoutBtn.setVisibility(View.GONE);
        binding.registerQrBtn.setVisibility(View.GONE);
        binding.editUserInfoBtn.setVisibility(View.GONE);
        binding.notiSettingBtn.setVisibility(View.GONE);
    }
    public void setLoginState(){
        binding.nonLoginGreeting.setVisibility(View.GONE);
        binding.nonLoginBoundaryLine.setVisibility(View.GONE);
        binding.loginGreeting.setVisibility(View.VISIBLE);
        binding.phoneInfo.setVisibility(View.VISIBLE);
        binding.registerQrBtn.setVisibility(View.VISIBLE);
        binding.editUserInfoBtn.setVisibility(View.VISIBLE);
        binding.notiSettingBtn.setVisibility(View.VISIBLE);

        //data mapping
        if(((MainActivity)getActivity()).userInfo!=null){
//            String strUserPhone = ((MainActivity)getActivity()).userInfo.getPhone();
//            mTvUserPhone.setText((PhoneNumberUtils.formatNumber(strUserPhone, Locale.getDefault().getCountry())));

            if(viewModel.getMutableData().getValue().getSecond_phone().equals("")){
                binding.secondPhoneTv.setVisibility(View.VISIBLE);  //2??????????????? TextView
                binding.editSecondPhoneBtn.setVisibility(View.VISIBLE); //2??????????????? ???????????????
                binding.registerSecondphone.setVisibility(View.GONE);  //2??????????????? ????????????
            }
            else{
                binding.secondPhoneTv.setVisibility(View.GONE);  //2??????????????? TextView
                binding.editSecondPhoneBtn.setVisibility(View.GONE);  //2??????????????? ???????????????
                binding.registerSecondphone.setVisibility(View.VISIBLE);  //2??????????????? ????????????
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
        //?????? ????????? ?????? ????????? ????????? ?????? ??????????????? ???????????? ?????? ??????(?????? ????????? ??? ????????? ???????????? ?????????)
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(X_ACCESS_TOKEN, null);
        editor.commit();

        System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));
        ((MainActivity)MainActivity.mContext).accountLogout();


        showCustomToast("??????????????? ???????????????????????????.");
    }

    @Override
    public void getLogoutFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_USERINFO_ACTIVITY){
            if(resultCode == RESULT_OK){
                UserInfo info = viewModel.getMutableData().getValue();
                info.setNickname(data.getStringExtra("nickname"));
                info.setPhone(data.getStringExtra("phone"));
                info.setBirth(data.getStringExtra("birth"));
                viewModel.setUserData(info);
            }
        }
    }
}