package com.egongil.numva_android_app.src.numvatalk;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.CHAT_LEFT;
import static com.egongil.numva_android_app.src.config.ApplicationClass.ViewType.CHAT_RIGHT;
import static com.mesibo.api.Mesibo.FLAG_DEFAULT;
import static com.mesibo.api.Mesibo.FLAG_READRECEIPT;
import static com.mesibo.api.Mesibo.MSGSTATUS_CALLINCOMING;
import static com.mesibo.api.Mesibo.MSGSTATUS_CALLOUTGOING;
import static com.mesibo.api.Mesibo.MSGSTATUS_OUTBOX;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.RecyclerTouchListener;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mesibo.api.Mesibo;
import com.mesibo.api.MesiboProfile;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.emojiview.EmojiconEditText;
import com.mesibo.emojiview.EmojiconGridView;
import com.mesibo.emojiview.EmojiconTextView;
import com.mesibo.emojiview.EmojiconsPopup;
import com.mesibo.emojiview.emoji.Emojicon;
import com.mesibo.messaging.AllUtils.LetterTileProvider;
import com.mesibo.messaging.AllUtils.TextToEmoji;
import com.mesibo.messaging.MesiboConfiguration;
import com.mesibo.messaging.MesiboImages;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.MesiboUIManager;
import com.mesibo.messaging.MessageAdapter;
import com.mesibo.messaging.MessageViewHolder;
import com.mesibo.messaging.MessagingActivityListener;
import com.mesibo.messaging.MessagingActivityNew;
import com.mesibo.messaging.MessagingFragment;
import com.mesibo.messaging.UserData;
import com.mesibo.messaging.Utils;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class NumvatalkActivity extends BaseActivity implements NumvatalkBottomDialog.BottomSheetListener, Mesibo.MessageListener, Mesibo.ConnectionListener, Mesibo.SyncListener, MessageViewHolder.ClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MesiboProfile.Listener, MessageAdapter.MessagingAdapterListener, MessagingActivityListener, MesiboMessagingFragment.FragmentListener {
    private static long ACTIVITY_DISPLAY_DURATION = 10000;
//    private ArrayList<ChatDataItem> mChatList;
    private ArrayList<String> mSimpleMessageList;

    private TextView mTvName;
    private TextView mTvSendBtn;
    private ImageView mIvBackBtn, mIvSimpleBtn;
    private LinearLayout mLlSimple, mLlSimpleBar, mLlOptionBtn;
    private EditText mEtMessage;
    private boolean isSimpleVisible = false;
    private RecyclerView mRvChat;

    //mesibo
    MesiboProfile mRemoteProfile;
    Mesibo.MessageParams mRemoteParams;
    Mesibo.ReadDbSession mReadSession;

    //Messaging Activity clone
    private WeakReference<MesiboMessagingFragment.FragmentListener> mListener = null;
    private com.mesibo.messaging.MesiboRecycleViewHolder.Listener mCustomViewListener = null;
    private ArrayList<MessageData> mMessageList = new ArrayList();
    private String mName = null;
    private String mPeer = null;
    private long mGroupId = 0L;
    private long mForwardId = 0L;
    private MesiboProfile mUser = null;
    private boolean mReadOnly = false;
    private boolean read_flag = false;
    private boolean mShowMissedCalls = true;
    private UserData mUserData = null;
    private String mPrevDateStamp = "";
    private long mlastDbTimestamp = 0L;
    private boolean mFirstDBMessage = true;
    private boolean mFirstRTMessage = true;
    private long mLastMessageId = 0L;
    private Mesibo.MessageParams mParameter = null;
    private int mNonDeliveredCount = 0;
    private int mLastMessageCount = 0;
    private boolean showLoadMore = true;
    private int mLastReadCount = 0;
    private ChatAdapter mAdapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_numvatalk);
        mTvName = findViewById(R.id.chatroom_tv_nickname);
        mIvBackBtn = findViewById(R.id.chatroom_iv_backbtn);
        mLlOptionBtn = findViewById(R.id.chatroom_optionbtn);
        mIvSimpleBtn = findViewById(R.id.chatroom_iv_quickbtn);
        mLlSimple = findViewById(R.id.chatroom_ll_simplemessage);
        mLlSimpleBar = findViewById(R.id.chatroom_ll_quick);
        mEtMessage = findViewById(R.id.chatroom_et_message);
        mTvSendBtn = findViewById(R.id.chatroom_tv_sendbtn);

        mIvBackBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        mLlOptionBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                NumvatalkBottomDialog bottomDialog = new NumvatalkBottomDialog();
                bottomDialog.show(getSupportFragmentManager(), "NumvatalkBottomDialog");
            }
        });
        mIvSimpleBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (!isSimpleVisible) {
                    mLlSimple.setVisibility(View.VISIBLE);
                    mIvSimpleBtn.setRotation(180f);
                    isSimpleVisible = true;
                } else {
                    mLlSimple.setVisibility(View.GONE);
                    mIvSimpleBtn.setRotation(0f);
                    isSimpleVisible = false;
                }
            }
        });


        mTvSendBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onSendMessage(mRemoteParams, mEtMessage.getText().toString());
                mEtMessage.setText("");
            }
        });


        //mesibo
//        this.setListener();
//        if (null == this.getListener()) {
//            this.finish();
//            return null;
//        } else {
            if (this instanceof com.mesibo.messaging.MesiboRecycleViewHolder.Listener) {
                this.mCustomViewListener = (com.mesibo.messaging.MesiboRecycleViewHolder.Listener) this;
            }

            Intent intent = getIntent();
            String peer = intent.getStringExtra("peer");
            long gid = intent.getLongExtra("groupid", 0L);
            if (null == this.mMessageList || null != peer && null != this.mPeer && 0 != this.mPeer.compareToIgnoreCase(peer) || gid > 0L && this.mGroupId > 0L && gid != this.mGroupId) {
                this.read_flag = false;
                if (null == this.mMessageList || this.mMessageList.size() > 0) {
                    this.mMessageList = new ArrayList();
                }
            }

            this.mPeer = peer;
            this.mGroupId = gid;
            long forwardid = intent.getLongExtra("mid", 0L);
            this.mReadOnly = intent.getBooleanExtra("readonly", false);
            boolean createProfile = intent.getBooleanExtra("createprofile", false);
            boolean hideReply = intent.getBooleanExtra("hidereply", false);
            this.mShowMissedCalls = intent.getBooleanExtra("missedcalls", true);

            this.mUser = Mesibo.getProfile(this.mPeer);


            this.mUser.addListener(this);

            if (Mesibo.isReady() && null != this.mUser) {
                if (null == this.mUser.other) {
                    this.mUser.other = new UserData(this.mUser);
                }

                this.mUserData = (UserData) this.mUser.other;

                this.mAdapter = new ChatAdapter(this, this, this.mMessageList, this);

                MesiboImages.init(this);
                this.mFirstRTMessage = true;
                this.mName = this.mUserData.getUserName();
                mTvName.setText(mName);

                Mesibo.addListener(this);

                this.mParameter = new Mesibo.MessageParams(this.mUserData.getPeer(), this.mUserData.getGroupId(), 3L, 0);
                if (forwardid > 0L) {
                    long mId = this.getMessageId();
                    Mesibo.forwardMessage(this.mParameter, mId, forwardid);
                    forwardid = 0L;
                }
            }
            initData();
//        }
//////////////////////////////
        mRvChat = findViewById(R.id.chatroom_rv_chat);
        LinearLayoutManager chatmanager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRvChat.setLayoutManager(chatmanager);
        mRvChat.setAdapter(mAdapter);

        LinearLayoutManager simplemanager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView mRvSimpleMessage = findViewById(R.id.chatroom_rv_simplemessage);
        mRvSimpleMessage.setLayoutManager(simplemanager);
        mRvSimpleMessage.setAdapter(new SimpleMessageAdapter(mSimpleMessageList));
        //recyclerview simple message 클릭 시 이벤트
        RecyclerTouchListener touchListener = new RecyclerTouchListener(this, mRvSimpleMessage);
        touchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                //아이템 클릭 시 동작
                mEtMessage.setText(mSimpleMessageList.get(position));
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        });
        mRvSimpleMessage.addOnItemTouchListener(touchListener);

        if(mAdapter.getItemCount()>0)
            this.mRvChat.smoothScrollToPosition(this.mAdapter.getItemCount() - 1);
    }
    private void initData(){
        //보내기 위
        mRemoteParams = new Mesibo.MessageParams(mPeer, FLAG_DEFAULT);
        Mesibo.setAppInForeground(this, 0, true);

//        mChatList = new ArrayList<>();
//        mChatList.add(new ChatDataItem("21.11.01(월)", null, true, ApplicationClass.ViewType.CHAT_CENTER));
//        mChatList.add(new ChatDataItem("안녕하세요.", "21:03", true, CHAT_LEFT));
//        mChatList.add(new ChatDataItem("무슨 일이시죠?", "21:04", true, CHAT_RIGHT));
//        mChatList.add(new ChatDataItem("차 범퍼를 긁었습니다.", "21:06", true, CHAT_LEFT));
//        mChatList.add(new ChatDataItem("지금 내려갈게요", "21:10", false, CHAT_RIGHT));

        mSimpleMessageList = new ArrayList<>();
        mSimpleMessageList.add("출차 부탁드립니다.");
        mSimpleMessageList.add("지금 출차해드릴게요.");
        mSimpleMessageList.add("잠시만 기다려 주세요.");
        mSimpleMessageList.add("고맙습니다.");
        mSimpleMessageList.add("전화를 받을 수 없습니다.");
        mSimpleMessageList.add("조금 뒤 연락드리겠습니다.");
    }

    @Override
    public void onButtonClicked(int tv_id) {
        //채팅방 오른쪽 상단 버튼 클릭시 발생하는 메뉴 clicked 함수
        showCustomToast(""+tv_id);
        if(tv_id == 0){
            //넘바콜 걸기
            if(!MesiboCall.getInstance().callUi(this, mPeer, false))
                MesiboCall.getInstance().callUiForExistingCall(this);

        }else if(tv_id == 1){
            //TODO: 채팅방 나가기
        }
    }

    //Mesibo message 전송 메소드
    public void onSendMessage(Mesibo.MessageParams param, String message){
//        mRemoteProfile.sendMessage(Mesibo.random(), message);
        int rv = Mesibo.sendMessage(param, Mesibo.random(), message);
        if(Mesibo.RESULT_OK == rv){
            Log.d("onSendMessage", "Message sent");
        }else{
            Log.d("onSendMessage", "Message failed: "+ rv);
        }
//        this.mAdapter.addRow();
//        this.mAdapter.notifyItemInserted(this.mMessageList.size());
//        this.mRvChat.smoothScrollToPosition(this.mAdapter.getItemCount() - 1);
        MessageData messageData = new MessageData(param, getMessageId(), null, mName, message, Mesibo.getTimestamp(), MSGSTATUS_OUTBOX, mGroupId);
        addMessage(mParameter,messageData);
    }

    //mesibo
    public void setListener(){
        this.mListener = new WeakReference((MesiboMessagingFragment.FragmentListener)this);
    }

    public MesiboMessagingFragment.FragmentListener getListener() {
        return null == this.mListener ? null : (MesiboMessagingFragment.FragmentListener)this.mListener.get();
    }


    private long getMessageId() {
        this.mLastMessageId = Mesibo.random();
        return this.mLastMessageId;
    }

    public void onStop(){
        if (null != this.mUserData && null != this.mReadSession) {
            this.mReadSession.enableReadReceipt(false);
        }

        super.onStop();
    }

    public void onDestroy() {
        if (null != this.mReadSession) {
            this.mReadSession.stop();
        }

        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        Utils.showServicesSuspendedAlert(this);
        this.mNonDeliveredCount = 0;
        Mesibo.setForegroundContext(this, 1, true);

        Mesibo.addListener(this);

        if (null != this.mUserData) {
            if (null == this.mReadSession) {
                this.mReadSession = new Mesibo.ReadDbSession(this.mUserData.getPeer(), this.mUserData.getGroupId(), (String)null, this);
                this.mReadSession.enableReadReceipt(true);
                this.mReadSession.enableMissedCalls(this.mShowMissedCalls);
                this.mReadSession.start();
            } else {
                this.mReadSession.enableReadReceipt(true);
            }

            if (!this.read_flag) {
                if (null != this.mCustomViewListener) {
                }

                this.read_flag = true;
                this.loadFromDB(50);
                int sizeNext = this.mMessageList.size();
                this.mAdapter.notifyItemRangeInserted(0, sizeNext);

            } else {
                this.mAdapter.notifyDataSetChanged();
            }

            if (0L == this.mGroupId) {
                this.sendActivity(10);
            }
        }
    }
    private void sendActivity(int activity) {
        int interval = (int)(ACTIVITY_DISPLAY_DURATION-2000);
        Mesibo.sendActivity(mParameter, 0, activity, interval);
    }

    private void loadFromDB(int count) {
        this.mLastMessageCount = this.mMessageList.size();
        this.showLoadMore = false;
        this.mFirstDBMessage = true;
        this.mLastReadCount = this.mReadSession.read(count);
        if (this.mLastReadCount == count) {
            this.showLoadMore = true;
        } else {
            this.mReadSession.sync(count, this);
        }

    }

    @Override
    public void Mesibo_onSync(int count) {
        final int c = count;
        if(count > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    loadFromDB(c);
                }
            });

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void Mesibo_onConnectionStatus(int i) {

    }

    public void updateUiIfLastMessage(Mesibo.MessageParams params) {
        if(!params.isLastMessage()) return;

        if(!params.isDbMessage()) return;

        if(mMessageList.size() > 0) {
            MessageData oldest = mMessageList.get(0);
            if (null != oldest && oldest.getType() != com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE) {
                mPrevDateStamp = oldest.getDateStamp();
                mMessageList.add(0, new MessageData(com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE, mlastDbTimestamp));
            }
        }

        // since we will add messages as well as date stamp, we must keep track of
        // all the newly added message
        mAdapter.notifyItemRangeInserted(0, mMessageList.size()-mLastMessageCount);
        this.mRvChat.smoothScrollToPosition(this.mAdapter.getItemCount() - 1);
        return;

    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams params, byte[] data) {
        Log.d("NumvatalkActivity", "Mesibo_onMessage");

        if(MSGSTATUS_CALLINCOMING == params.getStatus() || MSGSTATUS_CALLOUTGOING == params.getStatus()) {
            return true;
        }


        String str = "";
        try {
            str = new String(data, "UTF-8");
        } catch (Exception e) {
            return false;
        }

        MessageData cm = new MessageData(params, params.mid, params.peer, params.profile.getName(), str, params.ts,  params.getStatus() , mGroupId);

        addMessage(params, cm);

        updateUiIfLastMessage(params);
        return false;
    }
    private void addMessage(Mesibo.MessageParams params, MessageData m) {
        if (Mesibo.ORIGIN_DBMESSAGE == params.origin) {
            this.addTimestamp(m, false);
            this.mMessageList.add(0, m);
            this.mlastDbTimestamp = m.getTimestampMs();
        } else {
            addTimestamp(m, true);
            this.mMessageList.add(m);
            this.mAdapter.addRow();
            this.mAdapter.notifyItemInserted(this.mMessageList.size());
            this.mRvChat.smoothScrollToPosition(this.mAdapter.getItemCount() - 1);
        }
    }
    private void addTimestamp(MessageData m, boolean realtime) {
        // if we are loading from db, remove oldest timestamp
        Log.d("NumvatalkActivity", "addTimestamp");
        if(mFirstDBMessage && !realtime && mMessageList.size() > 0) {
            //remove oldest if it's a date type
            MessageData oldest = mMessageList.get(0);
            if (null != oldest && oldest.getType() == MessageData.MESSAGEDATA_TYPE_DATE) {
                mMessageList.remove(0);
                Log.d("NumvatalkActivity", "oldest remove");
            }
            mFirstDBMessage = false;
        }

        String ts = m.getDateStamp();

        // if empty, only add it for realtime message but record date stamp for both the cases
        if(0 == mMessageList.size()) {
            mPrevDateStamp = ts;
            mlastDbTimestamp = m.getTimestampMs();
            if(realtime){
                mMessageList.add(new MessageData(com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE, m.getTimestampMs()));
                Log.d("NumvatalkActivity", "date add");
            }
            return;
        }

        if(mPrevDateStamp.equalsIgnoreCase(ts)) {
            return;
        }

        // if realtime message, add current ts, else add ts of previous day
        if(realtime){
            if(mFirstRTMessage) {
                mFirstRTMessage = false;
                MessageData fistDbMessage = mMessageList.get(mMessageList.size()-1);
                String fistDbMessageDateStamp = fistDbMessage.getDateStamp();
                if(!fistDbMessageDateStamp.equalsIgnoreCase(ts)) {
                    mMessageList.add(new MessageData(com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE, m.getTimestampMs()));
                }
            }  else
                mMessageList.add(new MessageData(com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE, m.getTimestampMs()));

        }
        else
            mMessageList.add(0, new MessageData(com.mesibo.messaging.MessageData.MESSAGEDATA_TYPE_DATE, mlastDbTimestamp));

        mPrevDateStamp = ts;

    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {

    }

    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }

    @Override
    public void MesiboProfile_onUpdate(MesiboProfile mesiboProfile) {

    }

    @Override
    public boolean isMoreMessage() {
        return false;
    }

    @Override
    public void loadMoreMessages() {

    }

    @Override
    public void showMessageInvisible() {

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }

    @Override
    public void Mesibo_onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public boolean Mesibo_onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    @Override
    public int Mesibo_onGetEnabledActionItems() {
        return 0;
    }

    @Override
    public boolean Mesibo_onActionItemClicked(int item) {
        return false;
    }

    @Override
    public void Mesibo_onInContextUserInterfaceClosed() {

    }

    @Override
    public boolean Mesibo_onBackPressed() {
        return false;
    }

    @Override
    public void Mesibo_onUpdateUserPicture(MesiboProfile profile, Bitmap thumbnail, String picturePath) {

    }

    @Override
    public void Mesibo_onUpdateUserOnlineStatus(MesiboProfile profile, String status) {
    }

    @Override
    public void Mesibo_onShowInContextUserInterface() {

    }

    @Override
    public void Mesibo_onHideInContextUserInterface() {

    }

    @Override
    public void Mesibo_onContextUserInterfaceCount(int count) {

    }

    @Override
    public void Mesibo_onError(int type, String title, String message) {
        Utils.showAlert(this, title, message);
    }
}