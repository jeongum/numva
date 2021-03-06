package com.egongil.numva_android_app.src.home;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.FragmentHomeBinding;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.home.interfaces.HomeFragmentView;
import com.egongil.numva_android_app.src.home.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.main.models.MainViewModel;
import com.egongil.numva_android_app.src.qr_management.QrManagementActivity;

import java.util.ArrayList;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.PARKING_MEMO_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends BaseFragment implements HomeFragmentView {
    FragmentHomeBinding binding;
    Fragment fragment;

    ArrayList<SafetyInfo>mListQR;

    HomeQrViewPagerAdapter mViewPagerAdapter;
    public Callback mGetSafetyInfoCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();

        //MainActivity??? ViewModel ?????????
        MainViewModel viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        this.fragment = this;

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")!=""){
                    //????????????????????? ?????? ??????
                    ((MainActivity)getActivity()).callGetUser();

                    Callback mCallback = ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).mGetSafetyInfoCallback;
                    ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).getSafetyInfo(mCallback);
                }

                binding.refreshLayout.setRefreshing(false);
            }
        });

        binding.nonLoginGreeting.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.registerQr.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getActivity(), QrManagementActivity.class);
                startActivity(intent);
            }
        });

        binding.qrViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

        //??????,?????? page ????????????
        binding.qrViewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;

        int partialWidth = (int) (16 * density); //16dp -> ??????,?????? page ???????????? ??????
        int pageMargin = (int) (8 * density); //8dp -> ????????? ?????? ??????

        int viewPagerPadding = partialWidth + pageMargin;

        binding.qrViewPager.setPageMargin(pageMargin);
        binding.qrViewPager.setPadding(viewPagerPadding,0, viewPagerPadding,0);

        //Transformation
        Point screen = new Point();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screen);
        float startOffset = (float)(viewPagerPadding)/(float)(screen.x - 2 * viewPagerPadding);

        int baseElevation = 5;    //Minimum elevation of the view
        int rasingElevation = 5; //Amount of elevation to be raised when the view is at center
        //Elevation of view at center = baseElevation + raisingElevation
        float smallerScale = 0.8f; //Y scale of the view when it is at position 1 or -1

        binding.qrViewPager.setPageTransformer(false, new QrViewPagerTransformer(baseElevation, rasingElevation, smallerScale, startOffset));

        mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), this, mListQR);

        mGetSafetyInfoCallback = new Callback() {
            @Override
            public void callback() {
                if(mListQR!=null){
                    mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), fragment, mListQR);
                }else{
                    //????????? QR ?????? ??????, QR id -1??? item ?????? list ??????
                    mListQR = new ArrayList<SafetyInfo>();
                    mListQR.add(new SafetyInfo(-1));
                    mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), fragment, mListQR);
                }

                binding.qrViewPager.setAdapter(mViewPagerAdapter);
                binding.qrIndicator.setViewPager(binding.qrViewPager);

                mViewPagerAdapter.registerDataSetObserver(binding.qrIndicator.getDataSetObserver());
            }
        };
        getSafetyInfo(mGetSafetyInfoCallback);

        setHasOptionsMenu(true);

        setInitialLoginState();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == PARKING_MEMO_ACTIVITY){
                getSafetyInfo(mGetSafetyInfoCallback);
            }
        }
    }

    private void enableDisableSwipeRefresh(boolean enable){
        if(binding.refreshLayout!=null){
            binding.refreshLayout.setEnabled(enable);
        }
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
        binding.loginGreeting.setVisibility(View.GONE);
    }
    public void setLoginState(){
        binding.nonLoginGreeting.setVisibility(View.GONE);
        binding.loginGreeting.setVisibility(View.VISIBLE);

//        if(((MainActivity)getActivity()).userInfo!=null){
//            binding.userName.setText(((MainActivity)getActivity()).userInfo.getNickname());
//        }
    }
    public void getSafetyInfo(Callback mCallback){
        HomeService homeService = new HomeService(this);
        homeService.getSafetyInfo(mCallback);
    }

    @Override
    public void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse) {
        if(getSafetyInfoResponse!=null) {
            if (getSafetyInfoResponse.getCode() == 200 && getSafetyInfoResponse.isSuccess()) {
                //?????? ??? ??????
                mListQR = getSafetyInfoResponse.getResult();

                if(mListQR.size()!=0){
                    mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), this, mListQR);
                }
                else{
//                    //????????? QR ?????? ??????, QR id -1??? item ?????? ?????? list ??????(guide ????????? ????????? ?????? ????????????)
                    mListQR = new ArrayList<SafetyInfo>();
                    mListQR.add(new SafetyInfo(-1));
                    mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), this, mListQR);
                }
            }
        }
        else if(errorResponse != null){
            //?????? ??? ??????
            if(errorResponse.getCode() == -401){
                //???????????? ????????? ??????, QR id -1??? item ?????? list ??????(guide ????????? ????????? ?????? ????????????)
                mListQR = new ArrayList<SafetyInfo>();
                mListQR.add(new SafetyInfo(-1));
                mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), this, mListQR);

            }
        }
    }

    @Override
    public void getSafetyInfoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }
}