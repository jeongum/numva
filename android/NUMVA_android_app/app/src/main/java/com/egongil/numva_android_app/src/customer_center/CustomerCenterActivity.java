package com.egongil.numva_android_app.src.customer_center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.customer_center.interfaces.CustomerCenterActivityView;
import com.egongil.numva_android_app.src.customer_center.models.FAQResponse;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

import java.util.ArrayList;

public class CustomerCenterActivity extends BaseActivity implements CustomerCenterActivityView, ConnectionReceiver.ConnectionReceiverListener {
    private ArrayList<FAQRecyclerItem> mFAQList;
    FAQRecyclerAdapter mAdapter;
    RecyclerView mRvFAQ;
    Boolean isNetworkConnect;

    ImageView mIvExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_center);

        //네트워크 불안정
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkAvailable networkAvailable = new NetworkAvailable();
//        isNetworkConnect = networkAvailable.isNetworkAvailable(connectivityManager);
//        if(!isNetworkConnect){
//            Intent intent = new Intent(getApplicationContext(), NetworkFailureActivity.class);
//            startActivity(intent);
//        }

        checkConnection();

        mFAQList = new ArrayList<>();

        mIvExit = findViewById(R.id.customer_center_iv_crossbtn);
        mRvFAQ = findViewById(R.id.customer_center_rv);  //리스트


        getFAQ(); //FAQ불러오기

        ////////recyclerview//////////////
        initializeFAQList();
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRvFAQ.setLayoutManager(manager);
        //Adapter 객체 지정
        mAdapter = new FAQRecyclerAdapter(mFAQList);
        mRvFAQ.setAdapter(mAdapter);

        mIvExit.setOnClickListener(new OnSingleClickListener() {  //exit버튼
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mAdapter.setOnItemClickListener(new FAQRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(CustomerCenterActivity.this, CustomerCenterDetailActivity.class);
                intent.putExtra("id", mFAQList.get(position).getId());
                intent.putExtra("question", mFAQList.get(position).getQuestion());
                intent.putExtra("answer", mFAQList.get(position).getAnswer());
                startActivity(intent);
            }
        });
    }

    @Override
    public void getFAQSuccess(FAQResponse faqResponse, ErrorResponse errorResponse) {
        if(faqResponse != null){
            if(faqResponse.getCode()==200 && faqResponse.isSuccess()){
                mFAQList = faqResponse.getResult();
                initializeFAQList();
                ((FAQRecyclerAdapter)mRvFAQ.getAdapter()).updateData(mFAQList);

            }
        }
        else if (errorResponse != null){

        }
    }

    @Override
    public void getFAQFailure() {

    }

    public void initializeFAQList(){
        for(int i=0; i<mFAQList.size(); i++){
            mFAQList.get(i).getQuestion();
        }
    }

    public void getFAQ(){
        CustomerCenterService customerCenterService = new CustomerCenterService(this);
        customerCenterService.getFAQ();
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }else{
            recreate();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        ApplicationClass.getInstance().setConnectionListener(this);
    }

    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }

}