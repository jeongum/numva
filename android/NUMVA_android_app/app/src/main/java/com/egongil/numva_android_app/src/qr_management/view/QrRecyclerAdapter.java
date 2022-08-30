package com.egongil.numva_android_app.src.qr_management.view;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.models.SafetyInfo;
import com.egongil.numva_android_app.src.qr_management.viewmodel.QrManagementViewModel;

import java.util.ArrayList;

public class QrRecyclerAdapter extends RecyclerView.Adapter{
    private QrManagementViewModel mQrManagementViewModel;

    public QrRecyclerAdapter(QrManagementViewModel mQrManagementViewModel) {
        this.mQrManagementViewModel = mQrManagementViewModel;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_rv_qrlist, parent,false);

        return new QrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArrayList<SafetyInfo> mQrList = mQrManagementViewModel.getSafetyInfoData().getValue();
        String strQrName = mQrList.get(position).getName();
        Log.d("QrRecyclerAdapter", "strQrName : "+strQrName);
        ((QrViewHolder)holder).mtvQrname.setText(strQrName);
    }

    @Override
    public int getItemCount() {
        return mQrManagementViewModel.getSafetyInfoData().getValue().size();
    }

    public void updateData(ArrayList<SafetyInfo> mList){
        mQrManagementViewModel.setSafetyInfoData(mList);
        notifyDataSetChanged();
    }

    public class QrViewHolder extends RecyclerView.ViewHolder {
        TextView mtvQrname;

        public QrViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvQrname = itemView.findViewById(R.id.qrlist_tv_qrname);
        }
    }
}
