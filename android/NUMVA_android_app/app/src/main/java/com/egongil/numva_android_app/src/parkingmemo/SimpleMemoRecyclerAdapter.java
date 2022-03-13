package com.egongil.numva_android_app.src.parkingmemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.custom_dialogs.EditTextDialog;

import java.util.ArrayList;

public class SimpleMemoRecyclerAdapter extends RecyclerView.Adapter {
    Context mContext;
    private ArrayList<SimpleMemoRecyclerItem> mSimpleMemoList = null;

    public SimpleMemoRecyclerAdapter(ArrayList<SimpleMemoRecyclerItem> dataList)
    {
        mSimpleMemoList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == ApplicationClass.ViewType.SIMPLE_MEMO_VIEW){
            view = inflater.inflate(R.layout.item_rv_simplememo, parent, false);
            return new SimpleMemoViewHolder(view);
        }
        else if(viewType == ApplicationClass.ViewType.SIMPLE_MEMO_VIEW_ADD){
            view = inflater.inflate(R.layout.item_rv_simplememo_add, parent, false);
            return new SimpleMemoAddViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SimpleMemoViewHolder){
            String strMemo = mSimpleMemoList.get(position).getMemo();
            ((SimpleMemoViewHolder) holder).memo.setText(strMemo);

            if(strMemo!=null){
                ((SimpleMemoViewHolder) holder).memo.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorSemiBlack));
            }
        }
        else if(holder instanceof  SimpleMemoAddViewHolder){
            ((SimpleMemoAddViewHolder)holder).mLlAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSimpleMemoList.size() >= 6){
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.parking_memo_quickmemo_add_failure_max), Toast.LENGTH_LONG).show();
                    }
                    else{
                        EditTextDialog addMemoDialog = new EditTextDialog(mContext);
                        addMemoDialog.showDialog();
                        addMemoDialog.showKeyboard();
                        addMemoDialog.setBtnText("등록하기");
                        addMemoDialog.mLlClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addMemoDialog.closeKeyboard();
                                addMemoDialog.dismiss();
                            }
                        });
                        addMemoDialog.mTvSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String strAdd = addMemoDialog.mEtText.getText().toString();

                                ((ParkingMemoActivity)mContext).addSimpleMemo(strAdd);

                                addMemoDialog.closeKeyboard();
                                addMemoDialog.dismiss();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSimpleMemoList.size();
    }

    @Override
    public int getItemViewType(int position){
        return mSimpleMemoList.get(position).getViewType();
    }

    public void updateData(ArrayList<SimpleMemoRecyclerItem> mList){
        mSimpleMemoList.clear();
        mSimpleMemoList.addAll(mList);
        notifyDataSetChanged();
    }

    public class SimpleMemoViewHolder extends RecyclerView.ViewHolder{
        TextView memo;
        @SuppressLint("ClickableViewAccessibility")
        public SimpleMemoViewHolder(@NonNull View itemView) {
            super(itemView);
            memo = itemView.findViewById(R.id.simple_memo_tv_memo);
//            memo.scrollTo(0,0);

        }
    }

    public class SimpleMemoAddViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mLlAddItem;
        public SimpleMemoAddViewHolder(@NonNull View itemView) {
            super(itemView);
            mLlAddItem = itemView.findViewById(R.id.simple_memo_ll_additem);
        }
    }

}
