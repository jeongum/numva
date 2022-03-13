package com.egongil.numva_android_app.src.config;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.egongil.numva_android_app.R;

public class CustomViewTitlebar extends ConstraintLayout {
    ImageView mIvBackbtn;
    TextView mTvTitle;

    private static final int[] VISIBILITY_FLAGS = {VISIBLE, INVISIBLE, GONE};

    public CustomViewTitlebar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomViewTitlebar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        getAttrs(attrs);
    }

    public CustomViewTitlebar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        getAttrs(attrs, defStyleAttr);
    }

    public CustomViewTitlebar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        getAttrs(attrs, defStyleAttr);
    }

    private void init(Context context){
       View v = View.inflate(context, R.layout.customview_titlebar, this);

        mIvBackbtn = (ImageView)v.findViewById(R.id.titlebar_iv_backbtn);
        mTvTitle = (TextView)v.findViewById(R.id.titlebar_tv_text);

        mIvBackbtn.setOnClickListener(v1 -> ((Activity)context).finish());
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomViewTitlebar);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomViewTitlebar, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray){
        int backBtn_visibility = typedArray.getInt(R.styleable.CustomViewTitlebar_backBtn_visibility,0);
        mIvBackbtn.setVisibility(VISIBILITY_FLAGS[backBtn_visibility]);

        String title_text = typedArray.getString(R.styleable.CustomViewTitlebar_title_text);
        mTvTitle.setText(Html.fromHtml(title_text));

        typedArray.recycle();
    }
}
