package com.egongil.numva_android_app.src.qr_management;


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

import java.util.ArrayList;

public class QrRecyclerAdapter extends RecyclerView.Adapter{
    Context mContext;
    private ArrayList<SafetyInfo> mQrList = null;

    public QrRecyclerAdapter(ArrayList<SafetyInfo> mQrList) {
        this.mQrList = mQrList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_rv_qrlist, parent,false);

        return new QrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String strQrName = mQrList.get(position).getName();
        Log.d("QrRecyclerAdapter", "strQrName : "+strQrName);
        ((QrViewHolder)holder).mtvQrname.setText(strQrName);
    }

    @Override
    public int getItemCount() {
        return mQrList.size();
    }

    public void updateData(ArrayList<SafetyInfo> mList){
        mQrList.clear();
        mQrList.addAll(mList);
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
