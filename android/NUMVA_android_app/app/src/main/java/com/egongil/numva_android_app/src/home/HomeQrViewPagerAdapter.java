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
import com.egongil.numva_android_app.src.parkingmemo.ParkingMemoActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeQrViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private Fragment fragment;
    private ArrayList<SafetyInfo> mListQr;

    public HomeQrViewPagerAdapter(Context mContext, Fragment mFragment, ArrayList<SafetyInfo> mListQr) {
        this.mContext = mContext;
        this.fragment = mFragment;
        this.mListQr = mListQr;
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
            //???????????? ??????
            //qr??????
            mTvQrName.setText(R.string.home_nonregist_title);
            //????????????
            mTvParkingMemo.setText(R.string.home_nonlogin_guide);
            mTvParkingMemo.setTextColor(mContext.getResources().getColor(R.color.colorSemiBlack));

            //????????????
            mClNonRegistSafeNumber.setVisibility(View.VISIBLE);
            mClRegistSafeNumber.setVisibility(View.GONE);

            mTvNonRegistSafeNumber.setText(R.string.home_nonlogin_guide);

        }else{
            //????????? ??????
            if(mListQr.get(0).getId()!=-1){
                //????????? QR??? ?????? ??????
                mTvQrName.setText(mListQr.get(position).getName());

                //????????????
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

                //????????????
                mClNonRegistSafeNumber.setVisibility(View.GONE);
                mClRegistSafeNumber.setVisibility(View.VISIBLE);

                String strSafeNumber = mListQr.get(position).getSafety_number();
                if(strSafeNumber != null) {
                    //????????????
                    mTvRegistSafeNumber.setText(strSafeNumber);

                    //??????????????????
                    long nowTime = System.currentTimeMillis();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.KOREA);
                    Date date = new Date(nowTime + 60*60*1000); //?????? hour + 1
                    String nowTimeStr = dateFormat.format(date);

                    mTvRegistChangeTime.setText(nowTimeStr+":00");   //?????? changeTime ??????
                }else{
                    //????????? ??? ?????? ??????(???????????? ?????? QR????????? ??????)
                    mTvRegistSafeNumber.setText("000-0000-0000");
                    mTvRegistChangeTime.setText("00:00");
                }
            }
            else{
                //????????? qr??? ?????? ??????
                //TODO: ?????? ??? qr?????? ?????? ???????????? ??????
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
        if(mListQr != null){
            //Adapter??? ???????????? ????????? ???????????? ??? ??????
            return mListQr.size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //???????????? ?????? ?????? ??????????????? ??????
        return (view == (View)object);
    }

    //??????: PagerView?????? ???????????? ???????????? 10??????, ??? ????????? ????????? ???????????? ???(View)??? ???????????? ???????????? ?????????.
    // ?????? ????????? ????????? ????????? ??????, ??????????????? ?????? ????????? ???????????? ??? ????????? 2???,
    // ??? 3?????? ???????????? ?????? ?????? ??? ????????? ??????.
}
