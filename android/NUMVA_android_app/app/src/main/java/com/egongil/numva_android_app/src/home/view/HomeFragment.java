package com.egongil.numva_android_app.src.home.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.FragmentHomeBinding;
import com.egongil.numva_android_app.src.config.view.BaseFragment;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.config.models.SafetyInfo;
import com.egongil.numva_android_app.src.home.model.HomeService;
import com.egongil.numva_android_app.src.home.interfaces.HomeFragmentContract;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.main.viewmodels.MainViewModel;
import com.egongil.numva_android_app.src.qr_management.view.QrManagementActivity;

import java.util.ArrayList;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.PARKING_MEMO_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.QR_MANAGEMENT_ACTIVITY;

public class HomeFragment extends BaseFragment implements HomeFragmentContract {
    FragmentHomeBinding binding;
    Fragment fragment;
    MainViewModel mMainViewModel;
    ActivityResultLauncher<Intent> mActivityResultLauncher;

    private HomeService mHomeService;

    HomeQrViewPagerAdapter mViewPagerAdapter;

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
        mHomeService = new HomeService(this);

        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        binding.refreshLayout.setOnRefreshListener(() -> {
            if((((MainActivity)getActivity()).isLogin())){
                //로그인 상태일 경우
                ((MainActivity)getActivity()).getUser();
                mHomeService.getSafetyInfo();
            }

            binding.refreshLayout.setRefreshing(false);
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
                intent.putExtra("safety_info", mMainViewModel.getSafetyInfoData().getValue());
                mActivityResultLauncher.launch(intent);
            }
        });

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode()==PARKING_MEMO_ACTIVITY){//resultCode가 PARKING_MEMO_ACTIVITY로 넘어온다면
                Intent intent = result.getData();   //ActivityResult객체 result로 intent를 받아온다.
                int pos = intent.getIntExtra("safety_info_pos", -1);
                String memo = intent.getStringExtra("parking_memo");

                if(pos!=-1){
                    mMainViewModel.setParkingMemo(pos, memo);
                    setViewPager();
                }
            }else if(result.getResultCode()==QR_MANAGEMENT_ACTIVITY){
                Intent intent = result.getData();
                ArrayList<SafetyInfo>mListQR = (ArrayList<SafetyInfo>)intent.getSerializableExtra("safety_info");
                mMainViewModel.setSafetyInfoData(mListQR);
                setViewPager();
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

        //ViewPager margin, Transform
        setViewPagerStyle();

        mHomeService.getSafetyInfo();

        setHasOptionsMenu(true);

        return root;
    }
    private void setViewPagerStyle(){
        //이전,다음 page 미리보기
        binding.qrViewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;

        int partialWidth = (int) (16 * density); //16dp -> 이전,다음 page 보여지는 너비
        int pageMargin = (int) (8 * density); //8dp -> 페이지 사이 거리

        int viewPagerPadding = partialWidth + pageMargin;

        binding.qrViewPager.setPageMargin(pageMargin);
        binding.qrViewPager.setPadding(viewPagerPadding,0, viewPagerPadding,0);
        binding.qrViewPager.setPageTransformer(false, getViewPagerTransformer(viewPagerPadding));

    }
    private QrViewPagerTransformer getViewPagerTransformer(float viewPagerPadding){
        //Transformation
        Point screen = new Point();
        ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screen);
        float startOffset = (float)(viewPagerPadding)/(float)(screen.x - 2 * viewPagerPadding);

        int baseElevation = 5;    //Minimum elevation of the view
        int rasingElevation = 5; //Amount of elevation to be raised when the view is at center
        //Elevation of view at center = baseElevation + raisingElevation
        float smallerScale = 0.8f; //Y scale of the view when it is at position 1 or -1

        return new QrViewPagerTransformer(baseElevation, rasingElevation, smallerScale, startOffset);
    }
    private void enableDisableSwipeRefresh(boolean enable){
        if(binding.refreshLayout!=null){
            binding.refreshLayout.setEnabled(enable);
        }
    }

    @Override
    public void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse) {
        if(getSafetyInfoResponse!=null) {
            if (getSafetyInfoResponse.getCode() == 200 && getSafetyInfoResponse.isSuccess()) {
                //성공 시 동작
                ArrayList<SafetyInfo>mListQR = getSafetyInfoResponse.getResult();

                if (mListQR.size() == 0) {
                    //등록된 QR 없을 경우, 가이드아이템 추가
                    setViewPagerSafetyGuideItem();
                }else{
                    mMainViewModel.setSafetyInfoData(mListQR);
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
        setViewPager();
    }

    @Override
    public void getSafetyInfoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
        setViewPager();
    }

    //등록된 QR 없거나, 비로그인 상태일 경우
    //QR id -1로 item 담은 list를 ViewPager에 보낸다.(guide 출력할 아이템 하나)
    private void setViewPagerSafetyGuideItem(){
        ArrayList<SafetyInfo>mListQR = new ArrayList<>();
        mListQR.add(new SafetyInfo(-1));
        mMainViewModel.setSafetyInfoData(mListQR);
    }

    //ViewPager에 ViewPagerAdapter를 붙이고, indicator 설정
    private void setViewPager(){
        mViewPagerAdapter = new HomeQrViewPagerAdapter(getActivity(), mMainViewModel, mActivityResultLauncher);

        binding.qrViewPager.setAdapter(mViewPagerAdapter);
        binding.qrIndicator.setViewPager(binding.qrViewPager);

        mViewPagerAdapter.registerDataSetObserver(binding.qrIndicator.getDataSetObserver());
    }
}