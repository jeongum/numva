package com.egongil.numva_android_app.src.parkingmemo;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.SIMPLE_MEMO_VIEW;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.SIMPLE_MEMO_VIEW_ADD;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.config.RecyclerTouchListener;
import com.egongil.numva_android_app.src.custom_dialogs.EditTextDialog;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.home.HomeFragment;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoActivityView;
import com.egongil.numva_android_app.src.parkingmemo.models.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.parkingmemo.models.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.parkingmemo.models.UpdateSimpleMemoResponse;

import java.util.ArrayList;

public class ParkingMemoActivity extends BaseActivity implements ParkingMemoActivityView , ConnectionReceiver.ConnectionReceiverListener {
    public static Context mContext;
    private ArrayList<SimpleMemoRecyclerItem> mSimpleMemoList;
    private String initialMemo;
    private int safety_info_id;

    RecyclerView mRvSimpleMemo;
    EditText mEtNowMemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_memo);
        checkConnection(); //???????????? ?????? ??????
        mContext = this;
        mSimpleMemoList = new ArrayList<>();

        Intent intent = getIntent();
        safety_info_id = intent.getExtras().getInt("safety_info_id");

        View mRootView = findViewById(R.id.parking_memo_cl_rootView);
        ImageView mIvCrossBtn = findViewById(R.id.parking_memo_iv_crossbtn);
        mEtNowMemo = findViewById(R.id.parking_memo_et_now);
        LinearLayout mLlSaveBtn = findViewById(R.id.parking_memo_ll_savebtn_now);
        LinearLayout mLlResetBtn = findViewById(R.id.parking_memo_ll_resetbtn_now);
        mRvSimpleMemo = findViewById(R.id.parking_memo_rv_simple);
        LinearLayout mLlDeleteBtn = findViewById(R.id.parking_memo_ll_deletebtn);


        getParkingMemo();
        getSimpleMemo();

        //EditText ??? ???????????? ????????? ????????? focus ??????????????? ???
        editTextSetCancelable(mRootView, ParkingMemoActivity.this);

        //EditText focusOut?????? ??????????????? ?????????, focus In ?????? ???????????? ??????
        mEtNowMemo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    mEtNowMemo.setSelection(0);
                }
            }
        });

        mLlDeleteBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtNowMemo.setText(null);
            }
        });

        mIvCrossBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//
                if(!mEtNowMemo.getText().toString().equals(initialMemo)){
                    TwoButtonDialog confirmExitDialog = new TwoButtonDialog(ParkingMemoActivity.this);
                    confirmExitDialog.showDialog();
                    confirmExitDialog.setContextText("????????? ???????????? ?????????????????????? ???????????? ?????? ????????? ???????????????.");
                    confirmExitDialog.mBtnLeft.setOnClickListener(new OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View v) {
                            confirmExitDialog.dismiss();
                        }
                    });
                    confirmExitDialog.mBtnRight.setOnClickListener(new OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View v) {
                            confirmExitDialog.dismiss();
                            finish();
                        }
                    });
                }else{
                    finish();
                }
            }
        });

        mLlResetBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEtNowMemo.setText(initialMemo);
                hideKeyboard((ParkingMemoActivity)mContext);
                mEtNowMemo.clearFocus();
            }
        });
        mLlSaveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                initialMemo = mEtNowMemo.getText().toString();
                setParkingMemo(initialMemo);
                hideKeyboard((ParkingMemoActivity)mContext);
                mEtNowMemo.clearFocus();
            }
        });

        this.initializeSimpleMemoList();    //RecyclerView ????????? ?????????
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvSimpleMemo.setLayoutManager(manager);    //RecyclerView??? LayoutManager ??????
        mRvSimpleMemo.setAdapter(new SimpleMemoRecyclerAdapter(mSimpleMemoList));   //RecyclerView??? Adapter ??????

        //???????????? RecyclerView Swipe menu
        RecyclerTouchListener touchListener = new RecyclerTouchListener(this, mRvSimpleMemo);
        touchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                //????????? ?????? ??? ??????
                mEtNowMemo.setText(mSimpleMemoList.get(position).getMemo());
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        }).setSwipeOptionViews(R.id.simple_memo_rl_deletebtn, R.id.simple_memo_rl_editbtn)
                .setSwipeable(R.id.simple_memo_ll_FG, R.id.simple_memo_ll_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch(viewID){
                            case R.id.simple_memo_rl_deletebtn:
                                //swipemenu ???????????? ????????? ???
                                deleteSimpleMemo(mSimpleMemoList.get(position).getId());    //api
                                mSimpleMemoList.remove(position);
                                ((SimpleMemoRecyclerAdapter)mRvSimpleMemo.getAdapter()).updateData(mSimpleMemoList);
                                break;

                            case R.id.simple_memo_rl_editbtn:
                                //swipemenu ???????????? ????????? ???
                                EditTextDialog editDialog = new EditTextDialog(ParkingMemoActivity.this);
                                editDialog.showDialog();
                                editDialog.setBtnText("????????????");
                                editDialog.setEditText(mSimpleMemoList.get(position).getMemo());
                                editDialog.showKeyboard();
                                editDialog.mLlClose.setOnClickListener(new OnSingleClickListener() {
                                    @Override
                                    public void onSingleClick(View v) {
                                        editDialog.closeKeyboard();
                                        editDialog.dismiss();
                                    }
                                });
                                editDialog.mTvSave.setOnClickListener(new OnSingleClickListener() {
                                    @Override
                                    public void onSingleClick(View v) {
                                        String strEdit = editDialog.mEtText.getText().toString();
                                        editSimpleMemo(mSimpleMemoList.get(position).getId(), strEdit);    //api
                                        mSimpleMemoList.get(position).setMemo(strEdit);
                                        ((SimpleMemoRecyclerAdapter)mRvSimpleMemo.getAdapter()).updateData(mSimpleMemoList);
                                        editDialog.closeKeyboard();
                                        editDialog.dismiss();
                                    }
                                });
                                break;
                        }
                    }
                }).setIgnoredViewTypes(SIMPLE_MEMO_VIEW_ADD);
        mRvSimpleMemo.addOnItemTouchListener(touchListener);
    }

    public void initializeSimpleMemoList(){
        for(int i=0;i<mSimpleMemoList.size();i++){
            mSimpleMemoList.get(i).setViewType(SIMPLE_MEMO_VIEW);
        }
        mSimpleMemoList.add(new SimpleMemoRecyclerItem(-1, null , SIMPLE_MEMO_VIEW_ADD));
    }
    private void getParkingMemo(){
        ParkingMemoService parkingMemoService = new ParkingMemoService(this);
        GetParkingMemoRequest getParkingMemoRequest = new GetParkingMemoRequest();
        getParkingMemoRequest.setSafety_info_id(safety_info_id);
        parkingMemoService.getParkingMemo(getParkingMemoRequest);
    }

    private void setParkingMemo(String memo){
        ParkingMemoService parkingMemoService = new ParkingMemoService(this);
        SetParkingMemoRequest setParkingMemoRequest = new SetParkingMemoRequest();
        setParkingMemoRequest.setSafety_info_id(safety_info_id);
        setParkingMemoRequest.setMemo(memo);
        parkingMemoService.setparkingMemo(setParkingMemoRequest);
    };

    private void getSimpleMemo(){
        ParkingMemoService parkingMemoService = new ParkingMemoService(this);
        parkingMemoService.getSimpleMemo();
    }

    private void deleteSimpleMemo(int quickmemo_id){
        ParkingMemoService parkingMemoService = new ParkingMemoService(this);
        DeleteSimpleMemoRequest deleteSimpleMemoRequest = new DeleteSimpleMemoRequest();
        deleteSimpleMemoRequest.setQuickmemo_id(quickmemo_id);
        parkingMemoService.deleteSimpleMemo(deleteSimpleMemoRequest);
    }

    private void editSimpleMemo(int quickmemo_id, String memo){
        ParkingMemoService parkingMemoService = new ParkingMemoService(this);
        EditSimpleMemoRequest editSimpleMemoRequest = new EditSimpleMemoRequest();
        editSimpleMemoRequest.setMemo(memo);
        editSimpleMemoRequest.setQuickmemo_id(quickmemo_id);
        parkingMemoService.editSimpleMemo(editSimpleMemoRequest);
    }

    public void addSimpleMemo(String memo){
        ParkingMemoService parkingMemoService = new ParkingMemoService(this);
        AddSimpleMemoRequest addSimpleMemoRequest = new AddSimpleMemoRequest();
        addSimpleMemoRequest.setMemo(memo);
        parkingMemoService.addSimpleMemo(addSimpleMemoRequest);
    }

    @Override
    public void getParkingMemoSuccess(GetParkingMemoResponse getparkingMemoResponse, ErrorResponse errorResponse) {
        if(getparkingMemoResponse!=null){
            //getMemo ?????? ???
            if(getparkingMemoResponse.getCode()==200 && getparkingMemoResponse.isSuccess()){
                if(getparkingMemoResponse.getResult() != null){
                    initialMemo = getparkingMemoResponse.getResult().getMemo();
                }else{
                    initialMemo = null;
                }
                mEtNowMemo.setText(initialMemo);
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode()==-103){
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
                finish();
            }
        }
    }

    @Override
    public void getParkingMemoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void setParkingMemoSuccess(GetParkingMemoResponse getParkingMemoResponse, ErrorResponse errorResponse) {
        if(getParkingMemoResponse!=null){
            //setMemo ?????? ???
            if(getParkingMemoResponse.getCode() == 200 && getParkingMemoResponse.isSuccess()){
                showCustomToast(getResources().getString(R.string.parking_memo_save_success));

                //HomeFragment ????????????
                Callback mCallback = ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).mGetSafetyInfoCallback;
                ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).getSafetyInfo(mCallback);
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode() == -103){
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }
        }
    }

    @Override
    public void setParkingMemoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void getSimpleMemoSuccess(GetSimpleMemoResponse getSimpleMemoResponse, ErrorResponse errorResponse) {
        if(getSimpleMemoResponse!=null){
            if(getSimpleMemoResponse.getCode() == 200 && getSimpleMemoResponse.isSuccess()){
                mSimpleMemoList = getSimpleMemoResponse.getResult();
                initializeSimpleMemoList();
                ((SimpleMemoRecyclerAdapter)mRvSimpleMemo.getAdapter()).updateData(mSimpleMemoList);
            }
        }else if(errorResponse != null){
            if(errorResponse.getCode() == -103){
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }
        }
    }

    @Override
    public void getSimpleMemoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void deleteSimpleMemoSuccess(UpdateSimpleMemoResponse updateSimpleMemoResponse, ErrorResponse errorResponse) {
        if(updateSimpleMemoResponse!=null){
            if(updateSimpleMemoResponse.getCode()==200 && updateSimpleMemoResponse.isSuccess()){
                showCustomToast(getResources().getString(R.string.parking_memo_quickmemo_delete_success));
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode() == -103){
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }
        }
    }

    @Override
    public void deleteSimpleMemoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void editSimpleMemoSuccess(UpdateSimpleMemoResponse updateSimpleMemoResponse, ErrorResponse errorResponse) {
        if(updateSimpleMemoResponse!=null){
            if(updateSimpleMemoResponse.getCode()==200 && updateSimpleMemoResponse.isSuccess()){
                //???????????? ??????
                showCustomToast(getResources().getString(R.string.parking_memo_quickmemo_edit_success));
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode() == -103){
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }
        }
    }

    @Override
    public void editSimpleMemoFailure() {
        showCustomToast(getResources().getString(R.string.network_error));

    }

    @Override
    public void addSimpleMemoSucccess(AddSimpleMemoResponse addSimpleMemoResponse, ErrorResponse errorResponse) {
        if(addSimpleMemoResponse!=null){
            if(addSimpleMemoResponse.getCode()==200 && addSimpleMemoResponse.isSuccess()){
                //???????????? ??????
                showCustomToast(getResources().getString(R.string.parking_memo_quickmemo_add_success));
                getSimpleMemo();
                //TODO: getSimpleMemo ??????, response??? list??? add????????? ???????????? ??????
//                mSimpleMemoList.add(new SimpleMemoRecyclerItem())
//                ((SimpleMemoRecyclerAdapter)mRvSimpleMemo.getAdapter()).updateData(mSimpleMemoList);
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode()==-103){
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            }
            else if(errorResponse.getCode() == -601){
                showCustomToast(getResources().getString(R.string.parking_memo_quickmemo_add_failure_max));
            }
        }
    }

    @Override
    public void addSimpleMemoFailure() {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }else{
            //dismiss the dialog or refresh the activity
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //register connection status listener
        ApplicationClass.getInstance().setConnectionListener(this);

    }

    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }

}