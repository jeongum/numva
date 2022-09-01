package com.egongil.numva_android_app.src.second_phone.view;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.ItemRvSecondphoneBinding;
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
        ItemRvSecondphoneBinding binding = ItemRvSecondphoneBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull SecondPhoneRecyclerAdapter.ViewHolder holder, int position) {
        SecondPhoneRecyclerItem item = mSecondPhoneViewModel.getSecondPhone().getValue().get(position); //final로 선언하여 체크박스의 체크 상태값을 바뀌지 않도록 한다.
        String strSecondPhone = item.getSecondphone();
        holder.binding.secondphoneTvPhone.setText(strSecondPhone);

        if(strSecondPhone!=null){
            //2차전화번호 없을 때
            holder.binding.secondphoneTvPhone.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorSemiBlack));
        }
        //checkbox adapter
        holder.binding.secondphoneCheckbox.setOnCheckedChangeListener(null);
        holder.binding.secondphoneCheckbox.setChecked(item.getSelected());
        holder.binding.secondphoneCheckbox.setVisibility(mSecondPhoneViewModel.getEditState().getValue()? View.VISIBLE: View.GONE);
        holder.binding.secondphoneCheckbox.setOnClickListener(v -> {
            ArrayList<SecondPhoneRecyclerItem> secondPhone = mSecondPhoneViewModel.getSecondPhone().getValue();
            if(secondPhone.get(position).getSelected()){
                secondPhone.get(position).setSelected(false);
                mSecondPhoneViewModel.setSecondPhone(secondPhone);
            }else{
                secondPhone.get(position).setSelected(true);
                mSecondPhoneViewModel.setSecondPhone(secondPhone);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSecondPhoneViewModel.getSecondPhone().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemRvSecondphoneBinding binding;
        public ViewHolder(@NonNull ItemRvSecondphoneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    // 리스너 객체의 메서드 호출
                    // TODO: click position , api connection
                    if(mListener != null){
                        mListener.onItemClick(v, pos);
                    }
                    notifyItemChanged(pos);
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){

        this.mListener = listener;
    }

}
