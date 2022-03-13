package com.egongil.numva_android_app.src.numvatalk;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.CHAT_CENTER;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.CHAT_LEFT;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.CHAT_MISSEDCALL;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.CHAT_RIGHT;
import static com.mesibo.messaging.MesiboRecycleViewHolder.TYPE_CUSTOM;
import static com.mesibo.messaging.MesiboRecycleViewHolder.TYPE_DATETIME;
import static com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_CUSTOM;
import static com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.mesibo.api.Mesibo;
import com.mesibo.messaging.AllUtils.MyTrace;
import com.mesibo.messaging.MesiboRecycleViewHolder;
import com.mesibo.messaging.MessageAdapter;
import com.mesibo.messaging.MessageViewHolder;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {
//    public ArrayList<ChatDataItem> mChatList = null;

//    public ChatAdapter(ArrayList<ChatDataItem> mChatList) {
//        this.mChatList = mChatList;
//    }
//
private List<MessageData> mChatList = null;
    private MessageAdapter.MessagingAdapterListener mListener = null;
    private int mDisplayMsgCnt = 0;
    private int mTotalMessages = 0;
    private int mcellHeight = 0;
    private ProgressBar mProgress = null;
    private String mDateCoin = null;
    int mOriginalId = 0;
    private Context mContext = null;
    private MessageViewHolder.ClickListener clickListener = null;

    public ChatAdapter(Context context, MessageAdapter.MessagingAdapterListener listener, List<MessageData> ChatList, MessageViewHolder.ClickListener cl1) {
        this.mContext = context;
        this.mChatList = ChatList;
        this.mListener = listener;
        this.clickListener = cl1;
        this.mDisplayMsgCnt = 30;
        this.mDateCoin = "";
        this.mTotalMessages = this.mChatList.size();
        this.mDisplayMsgCnt = this.mTotalMessages;
        this.mcellHeight = 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType == CHAT_CENTER){
            view = inflater.inflate(R.layout.item_rv_center_numvatalk_chatroom, parent, false);
            return new CenterViewHolder(view);
        }else if(viewType == ApplicationClass.ViewType.CHAT_LEFT){
            view = inflater.inflate(R.layout.item_rv_received_numvatalk, parent, false);
            return new LeftViewHolder(view);
        }else if(viewType == CHAT_RIGHT){
            view = inflater.inflate(R.layout.item_rv_send_numvatalk, parent, false);
            return new RightViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.item_rv_received_numvatalk, parent, false);
            return new MissedViewHolder(view);
        }
    }

    //각 뷰 홀더에 데이터를 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyTrace.start("Messageing-BVH");
        if (position == 0) {
            // if(mDisplayMsgCnt != mTotalMessages)
            if (mListener.isMoreMessage()) {

                // we now don't show button instead we continuously load
                //((MessagingActivity) mContext).showMessgeVisible();
                mListener.loadMoreMessages();
            }

        } else {
            mListener.showMessageInvisible();
        }
        MessageData cm = mChatList.get(position);

        if(holder instanceof CenterViewHolder){
            String dateStamp = cm.getDateStamp();
            String dateKorea = dateStamp.substring(6,8)+"."+dateStamp.substring(3,5)+"."+dateStamp.substring(0,2);
            ((CenterViewHolder)holder).mTvContext.setText(dateKorea);
            Log.d("ChatAdapter", dateKorea);

        }else if(holder instanceof LeftViewHolder){
            ((LeftViewHolder)holder).mTvContext.setText(cm.getMessage());
            ((LeftViewHolder)holder).mTvTime.setText(cm.getTimestamp());
            Log.d("ChatAdapter", cm.getMessage());
        }else if(holder instanceof  MissedViewHolder){
            ((MissedViewHolder)holder).mTvContext.setText("Missed voice call\nat "+ cm.getTimestamp());
            ((MissedViewHolder)holder).mTvContext.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

            ((MissedViewHolder)holder).mTvContext.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            ((MissedViewHolder)holder).mTvTime.setText(cm.getTimestamp());
            Log.d("ChatAdapter", cm.getMessage());
        }else{
            ((RightViewHolder)holder).mTvContext.setText(cm.getMessage());
            ((RightViewHolder)holder).mTvTime.setText(cm.getTimestamp());
            Log.d("ChatAdapter", cm.getMessage());
            if(cm.getStatus()==Mesibo.MSGSTATUS_READ){
                //읽은 경우
                ((RightViewHolder)holder).mIvIsRead.setVisibility(View.GONE);
            }else{
                ((RightViewHolder)holder).mIvIsRead.setVisibility(View.VISIBLE);
            }
        }

        MyTrace.stop();
    }

    //리사이클러뷰 안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return mChatList.get(position).getViewType();

        MessageData data = mChatList.get(position);

        if(MESSAGEDATA_TYPE_DATE == data.getType()
        || data.getTimestamp() == null)
            return CHAT_CENTER;

        int status = mChatList.get(position).getStatus();
        if(Mesibo.MSGSTATUS_CALLMISSED == status)
            return CHAT_MISSEDCALL;

        if (Mesibo.MSGSTATUS_RECEIVEDNEW == status || Mesibo.MSGSTATUS_RECEIVEDREAD == status)
            return CHAT_LEFT;
        else
            return CHAT_RIGHT;
    }

    public void addRow() {
        mDisplayMsgCnt++;
        mTotalMessages = mChatList.size();
    }



    public class CenterViewHolder extends RecyclerView.ViewHolder{
        TextView mTvContext;
        public CenterViewHolder(@NonNull View itemView){
            super(itemView);
            mTvContext = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_center);
        }
    }
    public class LeftViewHolder extends RecyclerView.ViewHolder{
        TextView mTvContext;
        TextView mTvTime;
        public LeftViewHolder(@NonNull View itemView){
            super(itemView);
            mTvContext = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_received_context);
            mTvTime = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_received_time);
        }
    }
    public class RightViewHolder extends RecyclerView.ViewHolder{
        TextView mTvContext;
        TextView mTvTime;
        ImageView mIvIsRead;
        public RightViewHolder(@NonNull View itemView){
            super(itemView);
            mTvContext = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_send_context);
            mTvTime = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_send_time);
            mIvIsRead = (ImageView)itemView.findViewById(R.id.numvatalk_iv_item_isread);
        }
    }

    public class MissedViewHolder extends RecyclerView.ViewHolder{
        TextView mTvContext;
        TextView mTvTime;
        public MissedViewHolder(@NonNull View itemView){
            super(itemView);
            mTvContext = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_received_context);
            mTvTime = (TextView)itemView.findViewById(R.id.numvatalk_tv_item_received_time);
        }
    }
}
