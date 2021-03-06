package com.egongil.numva_android_app.src.qr_management;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RecyclerTouchListener;
import com.egongil.numva_android_app.src.custom_dialogs.OneLineEditDialog;
import com.egongil.numva_android_app.src.custom_dialogs.SelectTwoButtonDialog;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.home.HomeFragment;
import com.egongil.numva_android_app.src.home.HomeQrViewPagerAdapter;
import com.egongil.numva_android_app.src.home.models.SafetyInfo;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.qr_management.interfaces.QrManagementActivityView;
import com.egongil.numva_android_app.src.qr_management.models.DeleteQrRequest;
import com.egongil.numva_android_app.src.qr_management.models.DeleteQrResponse;
import com.egongil.numva_android_app.src.qr_management.models.GetSafetyInfoResponse;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_management.models.SetQrNameRequest;
import com.egongil.numva_android_app.src.qr_management.models.SetQrNameResponse;
import com.egongil.numva_android_app.src.qr_scan.QrScanActivity;

import java.util.ArrayList;

public class QrManagementActivity extends BaseActivity implements QrManagementActivityView {

    final static int QRNAME_VALID = 0;
    final static int QRNAME_EMPTY = 1;
    final static int QRNAME_EXIST = 2;

    RecyclerView mRvQrList;
    TextView mTvQrNotExist;
    Button mBtnRegister;
    ImageView mIvCloseBtn;

    ArrayList<SafetyInfo>mListQR;

    OneLineEditDialog directDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_management);

        mRvQrList = findViewById(R.id.qr_manage_rv_qrlist);
        mTvQrNotExist = findViewById(R.id.qr_manage_tv_qr_notexist);
        mBtnRegister = findViewById(R.id.qr_manage_btn_addQr);
        mIvCloseBtn = findViewById(R.id.qr_manage_iv_closebtn);
        mListQR = new ArrayList<>();

        mBtnRegister.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                showRegisterDialog();
            }
        });
        //RecyclerView ?????????
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvQrList.setLayoutManager(manager);
        mRvQrList.setAdapter(new QrRecyclerAdapter(mListQR));

        //RecyclerView Swipe menu
        RecyclerTouchListener touchListener = new RecyclerTouchListener(this, mRvQrList);
        touchListener.setSwipeOptionViews(R.id.qrlist_rl_deletebtn, R.id.qrlist_rl_editbtn)
                .setSwipeable(R.id.qrlist_ll_FG, R.id.qrlist_ll_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch(viewID){
                            case R.id.qrlist_rl_deletebtn:
                                //???????????? ????????? ???
                                showDeleteDialog(position);
                                break;
                            case R.id.qrlist_rl_editbtn:
                                //swipemenu ???????????? ????????? ???
                                showEditDialog(position);
                                break;
                        }
                    }
                });
        mRvQrList.addOnItemTouchListener(touchListener);

        getSafetyInfo();

        mIvCloseBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    public int checkQrNameValidity(String name){
        if(name.equals("")){
            return QRNAME_EMPTY;
        }
        for(int i=0; i<mListQR.size();i++){
            if(mListQR.get(i).getName().equals(name)){
                return QRNAME_EXIST;
            }
        }
        return QRNAME_VALID;
    }

    public void getSafetyInfo(){
        QrManagementService qrManagementService  = new QrManagementService(this);
        qrManagementService.getSafetyInfo();
    }

    @Override
    public void getSafetyInfoSuccess(GetSafetyInfoResponse getSafetyInfoResponse, ErrorResponse errorResponse) {
        if(getSafetyInfoResponse!=null) {
            if (getSafetyInfoResponse.getCode() == 200 && getSafetyInfoResponse.isSuccess()) {
                //?????? ??? ??????
                mListQR = getSafetyInfoResponse.getResult();
                ((QrRecyclerAdapter)mRvQrList.getAdapter()).updateData(mListQR);
//                ((HomeFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).;


                if(mListQR.size()!=0){
                    //????????? QR ?????? ??????
                    mTvQrNotExist.setVisibility(View.GONE);
                    mRvQrList.setVisibility(View.VISIBLE);
                }
                else{
//                    //????????? QR ?????? ??????
                    mTvQrNotExist.setVisibility(View.VISIBLE);
                    mRvQrList.setVisibility(View.GONE);
                }
            }
        }
        else if(errorResponse != null){
            //?????? ??? ??????
            if(errorResponse.getCode() == -103) {
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }else{
                showCustomToast(getResources().getString(R.string.network_error));
            }
        }
    }

    @Override
    public void getSafetyInfoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void setQrName(int id, String name){
        SetQrNameRequest setQrNameRequest = new SetQrNameRequest(id, name);

        QrManagementService qrManagementService  = new QrManagementService(this);
        qrManagementService.setQrName(setQrNameRequest);
    }

    @Override
    public void setQrNameSuccess(SetQrNameResponse setQrNameResponse, ErrorResponse errorResponse) {
        if(setQrNameResponse!=null) {
            if (setQrNameResponse.getCode() == 200 && setQrNameResponse.isSuccess()) {
                showCustomToast(getResources().getString(R.string.qr_manage_edit_success));
            }
        }
        else if(errorResponse != null){
            //?????? ??? ??????
            if(errorResponse.getCode() == -103) {
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }else{
                showCustomToast(getResources().getString(R.string.network_error));
            }
        }
    }

    @Override
    public void setQrNameFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void registerQr(String qr_id){
        RegisterQrRequest registerQrRequest = new RegisterQrRequest(qr_id);

        QrManagementService qrManagementService  = new QrManagementService(this);
        qrManagementService.registerQr(registerQrRequest);
    }

    @Override
    public void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse) {
        if(registerQrResponse!=null){
            if(registerQrResponse.getCode()==200 && registerQrResponse.isSuccess()){
                directDialog.dismiss();

                showCustomToast(getString(R.string.qr_manage_register_success));
                mListQR = registerQrResponse.getResult();

                ((QrRecyclerAdapter) mRvQrList.getAdapter()).updateData(mListQR); //RecyclerView ????????????
                updateHomeViewPager();  //HomeFragment??? Viewpaer ????????????

            }
        }
        else if(errorResponse != null){
            //?????? ??? ??????
            if(errorResponse.getCode() == -102){
                //?????? ????????? qr
                directDialog.setGuideText("?????? ????????? ?????????????????????. ?????? ??? ??? ??????????????????.");
                directDialog.setGuideColor(getColor(R.color.colorErrorRed));

            }else if(errorResponse.getCode() == -103){
                //???????????? ?????? ????????????
                directDialog.setGuideText("???????????? ?????? ?????????????????????. ?????? ??? ??? ??????????????????.");
                directDialog.setGuideColor(getColor(R.color.colorErrorRed));
            }else{
                showCustomToast(getResources().getString(R.string.network_error));
            }
        }
    }

    @Override
    public void registerQrFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void deleteQr(int id){
        DeleteQrRequest deleteQrRequest = new DeleteQrRequest(id);

        QrManagementService qrManagementService  = new QrManagementService(this);
        qrManagementService.deleteQr(deleteQrRequest);
    }

    @Override
    public void deleteQrSuccess(DeleteQrResponse deleteQrResponse, ErrorResponse errorResponse) {
        if(deleteQrResponse!=null){
            if(deleteQrResponse.getCode()==200 && deleteQrResponse.isSuccess()){
                showCustomToast(getString(R.string.qr_manage_delete_success));
            }
        }else if(errorResponse!=null){
            //?????? ??? ??????
            showCustomToast(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void deleteQrFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void showRegisterDialog(){
        SelectTwoButtonDialog registerDialog = new SelectTwoButtonDialog(QrManagementActivity.this);
        registerDialog.showDialog();

        registerDialog.setTitleText("??? QR ??????");
        registerDialog.setOptionText("?????? ???????????????", "QR ???????????????");

        //?????? ?????? ?????? ???
        registerDialog.mTvOption1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerDialog.dismiss();
                showDirectRegisterDialog();
            }
        });
        //qr?????? ?????? ???
        registerDialog.mTvOption2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                //qr?????? ????????? ??????
                Intent intent = new Intent(QrManagementActivity.this, QrScanActivity.class);
                startActivity(intent);
                registerDialog.dismiss();
                finish();
            }
        });
    }

    public void showDirectRegisterDialog(){
        directDialog = new OneLineEditDialog(QrManagementActivity.this);
        directDialog.showDialog();

        directDialog.setTitleText("?????? ???????????? ??????");
        directDialog.setEditHint("???????????? ??????");
        directDialog.setConfirmBtnText("????????????");


        directDialog.mTvConfirmBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerQr(directDialog.getTextOfEdit());
            }
        });
    }

    public void showEditDialog(int position){
        OneLineEditDialog editDialog = new OneLineEditDialog(QrManagementActivity.this);
        editDialog.showDialog();

        editDialog.setTitleText("QR ?????? ??????");    //????????? text ??????

        //editText ????????? ??????(8??????), ??????????????? ??????
        editDialog.mEtContext.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
        editDialog.setGuideText("????????? ?????? 8???????????? ???????????????.");
        editDialog.setGuideColor(getColor(R.color.colorPrimary));

        editDialog.setConfirmBtnText("????????????");   //?????? text ??????
        editDialog.setTextOfEdit(mListQR.get(position).getName());  //editText ?????? ??????

        editDialog.mTvConfirmBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                editDialog.dismiss();
                String strEdit = editDialog.getTextOfEdit();
                int isQrNameValid = checkQrNameValidity(strEdit);
                if(isQrNameValid == QRNAME_VALID) {
                    setQrName(mListQR.get(position).getId(), strEdit);  //api
                    mListQR.get(position).setName(strEdit);

                    ((QrRecyclerAdapter) mRvQrList.getAdapter()).updateData(mListQR); //RecyclerView ????????????
                    updateHomeViewPager();  //HomeFragment??? Viewpaer ????????????

                }
                else if(isQrNameValid == QRNAME_EMPTY) {
                    editDialog.setGuideText("??? ?????? ?????? ??????????????????.");
                    editDialog.setGuideColor(getColor(R.color.colorErrorRed));
                }
                else if(isQrNameValid == QRNAME_EXIST) {
                    editDialog.setGuideText("????????? ????????? ?????? QR??? ?????? ???????????????.");
                    editDialog.setGuideColor(getColor(R.color.colorErrorRed));
                }
            }
        });
    }

    public void showDeleteDialog(int position){
        TwoButtonDialog deleteDialog = new TwoButtonDialog(QrManagementActivity.this);
        deleteDialog.showDialog();
        deleteDialog.setContextText("??? QR??? ????????????????");
        deleteDialog.setSelectText("??????", "??????");

        deleteDialog.mBtnLeft.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.mBtnRight.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                deleteQr(mListQR.get(position).getId());    //api
                mListQR.remove(position);
                ((QrRecyclerAdapter)mRvQrList.getAdapter()).updateData(mListQR);
                updateHomeViewPager();
                deleteDialog.dismiss();
            }
        });
    }

    public void updateHomeViewPager(){
        Callback mCallback = ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).mGetSafetyInfoCallback;
        ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).getSafetyInfo(mCallback);

    }
}