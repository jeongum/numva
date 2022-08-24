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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.FragmentHomeBinding;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RetrofitService;
import com.egongil.numva_android_app.src.home.interfaces.HomeFragmentView;
import com.egongil.numva_android_app.src.home.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.main.viewmodels.MainViewModel;
import com.egongil.numva_android_app.src.main.viewmodels.MainViewModelFactory;
import com.egongil.numva_android_app.src.qr_management.QrManagementActivity;

import java.util.ArrayList;
import java.util.Objects;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.PARKING_MEMO_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.getRetrofitService;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

public class HomeFragment extends BaseFragment implements HomeFragmentView {
    FragmentHomeBinding binding;
    Fragment fragment;
    MainViewModel mMainViewModel;

    HomeQrViewPagerAdapter mViewPagerAdapter;
    public Callback mGetSafetyInfoCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();

        //MainActivity의 ViewModel 가져옴
        mMainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.setViewModel(mMainViewModel);
        binding.setLifecycleOwner(this);

        this.fragment = this;

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")!=""){
                    //비로그인상태가 아닐 경우
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

        //이전,다음 page 미리보기
        binding.qrViewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;

        int partialWidth = (int) (16 * density); //16dp -> 이전,다음 page 보여지는 너비
        int pageMargin = (int) (8 * density); //8dp -> 페이지 사이 거리

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

//        mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), mListQR);

        //getSafetyInfo()가 끝난 후, 실행할 콜백
        //TODO: success에서 처리해도 되는지 테스트
        mGetSafetyInfoCallback = () -> {
//            if (mListQR == null) {
//                //등록된 QR 없을 경우, QR id -1로 item 담은 list 보냄
//                mListQR = new ArrayList<>();
//                mListQR.add(new SafetyInfo(-1));
//            }
//            mMainViewModel.setSafetyInfoData(mListQR);
//            mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), mMainViewModel);
            binding.qrViewPager.setAdapter(mViewPagerAdapter);
            binding.qrIndicator.setViewPager(binding.qrViewPager);

            mViewPagerAdapter.registerDataSetObserver(binding.qrIndicator.getDataSetObserver());
        };

        //viewPagerAdapter가 ViewModel의 list를 observe하도록 함
//        mMainViewModel.mSafetyInfo.observe(this, safetyInfos -> mViewPagerAdapter.submitList(safetyInfos));

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
        if(Objects.equals(sSharedPreferences.getString(X_ACCESS_TOKEN, ""), "")) {
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
    }
    public void getSafetyInfo(Callback mCallback){
        HomeService homeService = new HomeService(this);
        homeService.getSafetyInfo(mCallback);
    }

    @Override
    public void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse) {
        if(getSafetyInfoResponse!=null) {
            if (getSafetyInfoResponse.getCode() == 200 && getSafetyInfoResponse.isSuccess()) {
                //성공 시 동작
                ArrayList<SafetyInfo>mListQR = getSafetyInfoResponse.getResult();

                if (mListQR.size() == 0) {
//                    //등록된 QR 없을 경우, 가이드아이템 추가
                    setViewPagerSafetyGuideItem();
                }else{
                    mMainViewModel.setSafetyInfoData(mListQR);
                    mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), mMainViewModel);
                }
            }
        }
        else if(errorResponse != null){
            //에러 시 동작
            if(errorResponse.getCode() == -401){
                //비로그인 상태일 경우 가이드아이템 추가
                setViewPagerSafetyGuideItem();
            }
        }
    }

    @Override
    public void getSafetyInfoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    //등록된 QR 없거나, 비로그인 상태일 경우
    //QR id -1로 item 담은 list를 ViewPager에 보낸다.(guide 출력할 아이템 하나)
    private void setViewPagerSafetyGuideItem(){
        ArrayList<SafetyInfo>mListQR = new ArrayList<SafetyInfo>();
        mListQR.add(new SafetyInfo(-1));
        mMainViewModel.setSafetyInfoData(mListQR);
        mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), mMainViewModel);
    }
}