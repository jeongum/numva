package com.egongil.numva_android_app.src.second_phone.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.second_phone.models.SecondPhoneRecyclerItem;
import com.egongil.numva_android_app.src.second_phone.viewmodel.SecondPhoneViewModel;

import java.util.ArrayList;

public class SecondPhoneRecyclerAdapter extends RecyclerView.Adapter<SecondPhoneRecyclerAdapter.ViewHolder> {
    private OnItemClickListener mListener = null; //리스너 객체 참조를 저장하는 변수
    private SecondPhoneViewModel mSecondPhoneViewModel;

    public SecondPhoneRecyclerAdapter(SecondPhoneViewModel mSecondPhoneViewModel) {
        this.mSecondPhoneViewModel = mSecondPhoneViewModel;
    }

    @NonNull
    @Override
    public SecondPhoneRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_secondphone, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull SecondPhoneRecyclerAdapter.ViewHolder holder, int position) {
        ArrayList<SecondPhoneRecyclerItem> mSecondPhoneList = mSecondPhoneViewModel.getSecondPhone().getValue();

        final SecondPhoneRecyclerItem item = mSecondPhoneViewModel.getSecondPhone().getValue().get(position); //final로 선언하여 체크박스의 체크 상태값을 바뀌지 않도록 한다.
        String strSecondPhone = mSecondPhoneList.get(position).getSecondphone();
        holder.tv_secondphone.setText(strSecondPhone);

        if(mSecondPhoneViewModel.getEditState().getValue()) {
            holder.cb_select.setVisibility(View.VISIBLE);
        }else{
            holder.cb_select.setVisibility(View.GONE);
        }

        if(strSecondPhone!=null){
            //2차전화번호 없을 때
            holder.tv_secondphone.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorSemiBlack));
        }


        //checkbox adapter
        holder.cb_select.setOnCheckedChangeListener(null);
        holder.cb_select.setChecked(item.getSelected());
        holder.cb_select.setOnClickListener(v -> {
            if(mSecondPhoneList.get(position).getSelected()){
                mSecondPhoneList.get(position).setSelected(false);

            }else{
                mSecondPhoneList.get(position).setSelected(true);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSecondPhoneViewModel.getSecondPhone().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_secondphone;
        CheckBox cb_select;

        @SuppressLint("ClickableViewAccessibility")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_secondphone = itemView.findViewById(R.id.secondphone_rv_phone);
            cb_select = itemView.findViewById(R.id.secondphone_rv_checkbox);
            cb_select.setChecked(false);


            tv_secondphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        // 리스너 객체의 메서드 호출
                        // TODO: click position , api connection
                        if(mListener != null){
                            mListener.onItemClick(v, pos);
                        }


                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }

//    public void updateData(ArrayList<SecondPhoneRecyclerItem> mList){
//        mSecondPhoneList.clear();
//        mSecondPhoneList.addAll(mList);
//        notifyDataSetChanged();
//    }


    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){

        this.mListener = listener;
    }

}
