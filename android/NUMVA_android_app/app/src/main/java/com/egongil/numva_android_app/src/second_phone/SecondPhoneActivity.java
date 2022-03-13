package com.egongil.numva_android_app.src.second_phone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.Callback;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.home.HomeFragment;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.mypage.MyPageFragment;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityView;
import com.egongil.numva_android_app.src.second_phone.models.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.second_phone.models.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.second_phone.models.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.models.SetSecondPhoneResponse;

import java.util.ArrayList;

public class SecondPhoneActivity extends BaseActivity implements SecondPhoneActivityView , ConnectionReceiver.ConnectionReceiverListener {
    private ArrayList<SecondPhoneRecyclerItem> mSecondPhoneList;
    SecondPhoneRecyclerAdapter mAdapter;

    TextView mTvEmpty;
    Button mBtnAdd, mBtnEdit;
    ImageView mIvExit;
    RecyclerView mRvSecondPhone;

    public static boolean isEditState = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_phone);
        checkConnection(); //네트워크 연결 확인

        isEditState = false;

        mSecondPhoneList = new ArrayList<>();

        mBtnAdd = findViewById(R.id.secondphone_btn_add); //추가버튼
        mBtnEdit = findViewById(R.id.secondphone_btn_edit); //편집버튼
        mIvExit = findViewById(R.id.secondphone_iv_crossbtn); //취소버튼(X)
        mTvEmpty = findViewById(R.id.secondphone_tv_empty); //리스트 없을 때
        mRvSecondPhone = findViewById(R.id.secondphone_rv); //리스트 있을 때

        getSecondPhone(); //2차 전화번호 불러오기

        /////////recyclerview//////
        initializeSecondPhoneList();
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRvSecondPhone.setLayoutManager(manager);
        // Adapter 객체 지정
        mAdapter = new SecondPhoneRecyclerAdapter(mSecondPhoneList);
        mRvSecondPhone.setAdapter(mAdapter);

        //x버튼
        mIvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //추가버튼
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditState==false) {
                    //추가 버튼
                    if (mSecondPhoneList.size() < 5) {
                        Intent intent = new Intent(getApplicationContext(), SecondPhoneRegisterActivity.class);
                        startActivity(intent);
                    } else {
                        showCustomToast("2차전화번호는 최대 5개까지 저장 가능합니다.");
                    }
                }
                else if(isEditState==true){
//                    //삭제 버튼
                    String strId = "";

                    for(int i=0; i<mAdapter.getItemCount();i++){
                        if(mSecondPhoneList.get(i).getSelected()==true){
                            strId += " "+mSecondPhoneList.get(i).getId();
                        }
                    }
                    delSecondPhone(strId);
                }
            }
        });




        //편집버튼
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditState==false){
                    //편집 버튼
                    isEditState=true;
                    setisEditState();

                    //기존 체크했던 항목 해제
                    for(int i=0; i<mAdapter.getItemCount();i++){
                        if(mSecondPhoneList.get(i).getSelected()==true){
                            mSecondPhoneList.get(i).setSelected(false);
                        }
                    }

                }
                else if(isEditState==true){
                    //취소 버튼
                    isEditState=false;
                    setisEditState();
                }
                mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
            }
        });

        mAdapter.setOnItemClickListener(new SecondPhoneRecyclerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                TwoButtonDialog setRepDialog = new TwoButtonDialog(SecondPhoneActivity.this);
                setRepDialog.showDialog();
                setRepDialog.setContextText("해당 2차전화번호를 등록하시겠습니까?");
                setRepDialog.setSelectText("취소", "확인");
                setRepDialog.mBtnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRepDialog.dismiss();
                    }
                });
                setRepDialog.mBtnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRepDialog.dismiss();
                        repSecondPhone(mSecondPhoneList.get(position).getId());
                    }
                });
            }
        });

    }


    //2차전화번호 불러오기 성공
    @Override
    public void getSecondPhoneSuccess(GetSecondPhoneResponse getSecondPhoneResponse, ErrorResponse errorResponse) {
        if(getSecondPhoneResponse != null){
            if(getSecondPhoneResponse.getCode()==200 && getSecondPhoneResponse.isSuccess()){
                mSecondPhoneList = getSecondPhoneResponse.getResult();
                initializeSecondPhoneList();
                ((SecondPhoneRecyclerAdapter)mRvSecondPhone.getAdapter()).updateData(mSecondPhoneList);
                if(getSecondPhoneResponse.getResult().isEmpty()){
                    //배열 비어있을 때
                    mTvEmpty.setVisibility(View.VISIBLE);
                    mRvSecondPhone.setVisibility(View.GONE);
                }
            }
            else if (errorResponse != null){
                //리스트 비어있을 때
            }
        }
    }

    //2차전화번호 불러오기 실패
    @Override
    public void getSecondPhoneFailure() {

    }

    //2차전화번호 대표번호 설정 성공
    @Override
    public void repSecondPhoneSuccess(RepSecondPhoneResponse repSecondPhoneResponse, ErrorResponse errorResponse) {
        if(repSecondPhoneResponse != null){
            if(repSecondPhoneResponse.getCode()==200 && repSecondPhoneResponse.isSuccess()){
                System.out.println("TOKEN : "+repSecondPhoneResponse.getMessage());

                showCustomToast("2차전화번호 대표번호가 설정되었습니다.");
                ((MainActivity)MainActivity.mContext).callGetUser();

                finish();
                }
            else if(errorResponse != null){
                //매칭된 데이터 없음
            }
        }
    }

    //2차전화번호 대표번호 설정 실패
    @Override
    public void repSecondPhoneFailure() {

    }

    //2차전화번호 삭제 성공
    @Override
    public void deleteSecondPhoneSuccess(DeleteSecondPhoneResponse deleteSecondPhoneResponse, ErrorResponse errorResponse) {
        if(deleteSecondPhoneResponse != null){
            if(deleteSecondPhoneResponse.getCode()==200 && deleteSecondPhoneResponse.isSuccess()){
                System.out.println("TOKEN : "+deleteSecondPhoneResponse.getMessage());
                showCustomToast("해당 전화번호가 삭제되었습니다.");
                ((MainActivity)MainActivity.mContext).callGetUser();
                Intent intent = new Intent(SecondPhoneActivity.this, SecondPhoneActivity.class);
                finish();
                startActivity(intent);
            }
            else if(errorResponse != null){

            }
        }
    }

    //2차전화번호 삭제 실패
    @Override
    public void deleteSecondPhoneFailure() {

    }

    public void initializeSecondPhoneList(){
        for(int i=0;i<mSecondPhoneList.size(); i++){
            mSecondPhoneList.get(i).getSecondphone();
        }
    }

    public void getSecondPhone(){
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        secondPhoneService.getSecondPhone();
    }

    public void repSecondPhone(int id){
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        RepSecondPhoneRequest repSecondPhoneRequest = new RepSecondPhoneRequest();
        repSecondPhoneRequest.setId(id);
        secondPhoneService.repSecondPhone(repSecondPhoneRequest);
    }

    public void delSecondPhone(String id){
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        DeleteSecondPhoneRequest deleteSecondPhoneRequest = new DeleteSecondPhoneRequest();
        deleteSecondPhoneRequest.setId(id);
        secondPhoneService.deleteSecondPhone(deleteSecondPhoneRequest);
    }

    private void setisEditState(){
        if (isEditState == false){
            mBtnAdd.setText("추가");
            mBtnEdit.setText("편집");
        }
        else if(isEditState == true){
            mBtnAdd.setText("삭제");
            mBtnEdit.setText("취소");
        }
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