package com.egongil.numva_android_app.src.customer_center;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.second_phone.SecondPhoneRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FAQRecyclerAdapter extends RecyclerView.Adapter<FAQRecyclerAdapter.ViewHolder> {
    private ArrayList<FAQRecyclerItem> mFAQList = null;
    private FAQRecyclerAdapter.OnItemClickListener mListener = null;

    FAQRecyclerAdapter(ArrayList<FAQRecyclerItem> dataList){
        mFAQList = dataList;
    }


    @NonNull
    @Override
    public FAQRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_customercenter, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NotNull FAQRecyclerAdapter.ViewHolder holder, int position) {
//        FAQRecyclerItem item = mFAQList.get(position);
        String strQuestion = mFAQList.get(position).getQuestion();
        holder.tv_question.setText(strQuestion);

    }

    @Override
    public int getItemCount() {
        return mFAQList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_arrow;
        TextView tv_question;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_arrow = itemView.findViewById(R.id.faq_rv_iv);
            tv_question = itemView.findViewById(R.id.faq_rv_question);

            tv_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v, pos);
                        }
                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }

    public void updateData(ArrayList<FAQRecyclerItem> mList){
        mFAQList.clear();
        mFAQList.addAll(mList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}
