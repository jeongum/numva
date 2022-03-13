package com.egongil.numva_android_app.src.mypage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.egongil.numva_android_app.R;

public class CustomViewMyPageListItem extends ConstraintLayout {
    TextView mTvtitle;
    ImageView mIvimg;
    public CustomViewMyPageListItem(Context context){
        super(context);
        initView();
    }

    public CustomViewMyPageListItem(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomViewMyPageListItem(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
        View v = li.inflate(R.layout.customview_mypage_list_item, this, false);
        addView(v);

        mTvtitle = (TextView)findViewById(R.id.mypage_list_item_tv_title);
        mIvimg = (ImageView)findViewById(R.id.mypage_list_item_iv_img);
    }
    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomViewMyPageListItem);
        setTypeArray(typedArray);
    }
    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomViewMyPageListItem, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray){
        String title_text = typedArray.getString(R.styleable.CustomViewMyPageListItem_mypage_item_text);
        mTvtitle.setText(title_text);

        int textColor = typedArray.getColor(R.styleable.CustomViewMyPageListItem_mypage_item_text_color, getResources().getColor(R.color.colorPrimary) );
        mTvtitle.setTextColor(textColor);

        int img_resID = typedArray.getResourceId(R.styleable.CustomViewMyPageListItem_mypage_item_image_src, R.drawable.ic_right_arrow_primary);
        mIvimg.setImageResource(img_resID);

        typedArray.recycle();
    }
    void setText(String strText){
        mTvtitle.setText(strText);
    }
    void setTextColor(int textColor){
        mTvtitle.setTextColor(textColor);
    }
    void setImageResource(int img_resID){
        mIvimg.setImageResource(img_resID);
    }
}
