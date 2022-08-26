package com.egongil.numva_android_app.src.parkingmemo.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.PARKING_MEMO_ACTIVITY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.SIMPLE_MEMO_VIEW;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.SIMPLE_MEMO_VIEW_ADD;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.view.RecyclerTouchListener;
import com.egongil.numva_android_app.src.custom_dialogs.EditTextDialog;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.home.view.HomeFragment;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.main.viewmodels.MainViewModel;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.parkingmemo.model.ParkingMemoService;
import com.egongil.numva_android_app.src.parkingmemo.model.SimpleMemoRecyclerItem;
import com.egongil.numva_android_app.src.parkingmemo.interfaces.ParkingMemoActivityContract;
import com.egongil.numva_android_app.src.config.models.request.AddSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.AddSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.request.DeleteSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.EditSimpleMemoRequest;
import com.egongil.numva_android_app.src.config.models.request.GetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.GetParkingMemoResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSimpleMemoResponse;
import com.egongil.numva_android_app.src.config.models.request.SetParkingMemoRequest;
import com.egongil.numva_android_app.src.config.models.response.UpdateSimpleMemoResponse;

import java.util.ArrayList;

public class ParkingMemoActivity extends BaseActivity implements ParkingMemoActivityContract, ConnectionReceiver.ConnectionReceiverListener {
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
        checkConnection(); //네트워크 연결 확인
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

        //EditText 밖 클릭하면 키보드 닫히고 focus 해제하도록 함
        editTextSetCancelable(mRootView, ParkingMemoActivity.this);

        //EditText focusOut되면 최상단으로 스크롤, focus In 되면 최하단에 커서
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
                    confirmExitDialog.setContextText("수정을 취소하고 나가시겠습니까? 저장하지 않은 메모는 삭제됩니다.");
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

                setResultCallback(initialMemo); //MainViewModel에 전달
            }
        });

        this.initializeSimpleMemoList();    //RecyclerView 리스트 초기화
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvSimpleMemo.setLayoutManager(manager);    //RecyclerView에 LayoutManager 등록
        mRvSimpleMemo.setAdapter(new SimpleMemoRecyclerAdapter(mSimpleMemoList));   //RecyclerView에 Adapter 등록

        //간편메모 RecyclerView Swipe menu
        RecyclerTouchListener touchListener = new RecyclerTouchListener(this, mRvSimpleMemo);
        touchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                //아이템 클릭 시 동작
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
                                //swipemenu 삭제버튼 눌렀을 때
                                deleteSimpleMemo(mSimpleMemoList.get(position).getId());    //api
                                mSimpleMemoList.remove(position);
                                ((SimpleMemoRecyclerAdapter)mRvSimpleMemo.getAdapter()).updateData(mSimpleMemoList);
                                break;

                            case R.id.simple_memo_rl_editbtn:
                                //swipemenu 수정버튼 눌렀을 때
                                EditTextDialog editDialog = new EditTextDialog(ParkingMemoActivity.this);
                                editDialog.showDialog();
                                editDialog.setBtnText("수정하기");
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
        parkingMemoService.setParkingMemo(setParkingMemoRequest);
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
            //getMemo 성공 시
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
            //setMemo 성공 시
            if(getParkingMemoResponse.getCode() == 200 && getParkingMemoResponse.isSuccess()){
                showCustomToast(getResources().getString(R.string.parking_memo_save_success));

                //HomeFragment 주차메모
                ((HomeFragment)((MainActivity)MainActivity.mContext).getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).mHomeService.getSafetyInfo();
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
                //성공했을 경우
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
                //성공했을 경우
                showCustomToast(getResources().getString(R.string.parking_memo_quickmemo_add_success));
                getSimpleMemo();
                //TODO: getSimpleMemo 대신, response로 list에 add해주는 방식으로 변경
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

    private void setResultCallback(String memo){
        int pos = getIntent().getIntExtra("safety_info_pos", -1);
        if(pos != -1){
            Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class);
            finish_intent.putExtra("safety_info_pos", pos);
            finish_intent.putExtra("parking_memo", memo);
            setResult(PARKING_MEMO_ACTIVITY, finish_intent);
        }
    }
}