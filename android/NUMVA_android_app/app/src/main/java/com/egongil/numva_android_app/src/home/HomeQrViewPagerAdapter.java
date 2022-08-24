package com.egongil.numva_android_app.src.home;

import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.egongil.numva_android_app.src.main.viewmodels.MainViewModel;
import com.egongil.numva_android_app.src.parkingmemo.ParkingMemoActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeQrViewPagerAdapter extends PagerAdapter {
    private Context mContext;
//    private ArrayList<SafetyInfo> mListQr;
    private MainViewModel mMainViewModel;

    public HomeQrViewPagerAdapter(Context mContext, ArrayList<SafetyInfo> mListQr) {
        this.mContext = mContext;
//        this.mListQr = mListQr;
    }

    public HomeQrViewPagerAdapter(Context mContext, MainViewModel mMainViewModel) {
        this.mContext = mContext;
        this.mMainViewModel = mMainViewModel;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vp_home_qr, null);

        TextView mTvQrName = view.findViewById(R.id.home_tv_qr_name);

        LinearLayout mLlParkingMemo = view.findViewById(R.id.home_ll_parking_memo);
        TextView mTvParkingMemo = view.findViewById(R.id.home_tv_parking_memo_context);

        ConstraintLayout mClNonRegistSafeNumber = view.findViewById(R.id.home_cl_nonregist_safe_number);
        TextView mTvNonRegistSafeNumber = view.findViewById(R.id.home_tv_nonregist_safe_number_context);

        ConstraintLayout mClRegistSafeNumber = view.findViewById(R.id.home_cl_regist_safe_number);
        TextView mTvRegistSafeNumber = view.findViewById(R.id.home_tv_regist_safe_number);
        TextView mTvRegistChangeTime = view.findViewById(R.id.home_tv_regist_changetime);

        if(sSharedPreferences.getString(X_ACCESS_TOKEN,"")=="") {
            //비로그인 상태
            //qr이름
            mTvQrName.setText(R.string.home_nonregist_title);
            //주차번호
            mTvParkingMemo.setText(R.string.home_nonlogin_guide);
            mTvParkingMemo.setTextColor(mContext.getResources().getColor(R.color.colorSemiBlack));

            //안심번호
            mClNonRegistSafeNumber.setVisibility(View.VISIBLE);
            mClRegistSafeNumber.setVisibility(View.GONE);

            mTvNonRegistSafeNumber.setText(R.string.home_nonlogin_guide);

        }else{
            ArrayList<SafetyInfo> mListQr = mMainViewModel.getSafetyInfoData().getValue();
            //로그인 상태
            if(mListQr.get(0).getId()!=-1){
                //등록된 QR이 있는 경우
                mTvQrName.setText(mListQr.get(position).getName());

                //주차메모
                String strParkingMemo = mListQr.get(position).getMemo();
                if(strParkingMemo!=null){
                    mTvParkingMemo.setText(strParkingMemo);
                    mTvParkingMemo.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }else{
                    mTvParkingMemo.setText(R.string.home_parking_memo_guide);
                    mTvParkingMemo.setTextColor(mContext.getResources().getColor(R.color.colorSemiBlack));
                }
                mLlParkingMemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ParkingMemoActivity.class);
                        intent.putExtra("safety_info_id", mListQr.get(position).getId());
                        mContext.startActivity(intent);
                    }
                });

                //안심번호
                mClNonRegistSafeNumber.setVisibility(View.GONE);
                mClRegistSafeNumber.setVisibility(View.VISIBLE);

                String strSafeNumber = mListQr.get(position).getSafety_number();
                if(strSafeNumber != null) {
                    //안심번호
                    mTvRegistSafeNumber.setText(strSafeNumber);

                    //변경에정시각
                    long nowTime = System.currentTimeMillis();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.KOREA);
                    Date date = new Date(nowTime + 60*60*1000); //현재 hour + 1
                    String nowTimeStr = dateFormat.format(date);

                    mTvRegistChangeTime.setText(nowTimeStr+":00");   //실제 changeTime 맵핑
                }else{
                    //실행될 리 없는 부분(안심번호 없는 QR번호판 없음)
                    mTvRegistSafeNumber.setText("000-0000-0000");
                    mTvRegistChangeTime.setText("00:00");
                }
            }
            else{
                //등록된 qr이 없는 경우
                //TODO: 클릭 시 qr코드 등록 화면으로 이동
                mTvQrName.setText(R.string.home_nonregist_title);

                mTvParkingMemo.setText(R.string.home_nonregist_guide);

                mClNonRegistSafeNumber.setVisibility(View.VISIBLE);
                mClRegistSafeNumber.setVisibility(View.GONE);
                mTvNonRegistSafeNumber.setText(R.string.home_nonregist_guide);
            }

        }

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        ArrayList<SafetyInfo> mListQr = mMainViewModel.getSafetyInfoData().getValue();
        if(mListQr != null){
            //Adapter가 관리하는 데이터 리스트의 총 개수
            return mListQr.size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //페이지가 특정 키와 연관되는지 체크
        return (view == (View)object);
    }

//    void submitList(ArrayList<SafetyInfo> list){
//        mListQr = list;
//        notifyDataSetChanged();
//    }

    //참고:
    // PagerView에서 관리하는 데이터가 10개면, 각 데이터 항목에 해당하는 뷰(View)는 한꺼번에 생성되지 않는다.
    // 현재 화면에 보이는 페이지 하나, 스와이프를 통해 좌우로 전환하게 될 페이지 2개,
    // 총 3개의 페이지에 대해 생성 및 관리를 한다.
}
