package com.egongil.numva_android_app.src.numvatalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;

import java.util.ArrayList;

public class SimpleMessageAdapter extends RecyclerView.Adapter{
    public ArrayList<String> mSimpleMsgList = null;
    public SimpleMessageAdapter(ArrayList<String> mSimpleMsgList){
        this.mSimpleMsgList = mSimpleMsgList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_rv_numvatalk_simplemessage, parent, false);

        return new SimpleMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SimpleMessageViewHolder)holder).mTvContext.setText(mSimpleMsgList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSimpleMsgList.size();
    }
    public class SimpleMessageViewHolder extends RecyclerView.ViewHolder{
        TextView mTvContext;
        public SimpleMessageViewHolder(@NonNull View itemView){
            super(itemView);
            mTvContext = itemView.findViewById(R.id.numvatalk_simplemessage_tv);
        }
    }
}
