package com.egongil.numva_android_app.src.numvatalk;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.NUMVATALK_CALL;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.NUMVATALK_EXIT;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class NumvatalkBottomDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogFragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_numvatalk_bottom, container, false);

        TextView mTvNumvaCallBtn = v.findViewById(R.id.numvatalk_bottomdialog_tv_numvacall);
        TextView mTvExitBtn = v.findViewById(R.id.numvatalk_bottomdialog_tv_exit);
        TextView mTvCancelBtn = v.findViewById(R.id.numvatalk_bottomdialog_tv_cancel);

        mTvNumvaCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(NUMVATALK_CALL);
                dismiss();
            }
        });

        mTvExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(NUMVATALK_EXIT);
                dismiss();
            }
        });

        mTvCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListener{
        void onButtonClicked(int tv_id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mListener = (BottomSheetListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement BottomSheetListener");
        }
    }
}
