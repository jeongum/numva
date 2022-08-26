package com.egongil.numva_android_app.src.numvatalk.chatlist;

import static com.egongil.numva_android_app.src.config.ApplicationClass.MESIBO_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;
import static com.egongil.numva_android_app.src.qr_scan.QrScanResultActivity.getRandomColor;
import static com.mesibo.api.Mesibo.MSGSTATUS_CALLINCOMING;
import static com.mesibo.api.Mesibo.MSGSTATUS_CALLOUTGOING;
import static com.mesibo.messaging.MesiboConfiguration.MESSAGE_DELETED_STRING;
import static com.mesibo.messaging.MesiboConfiguration.MISSED_VIDEO_CALL;
import static com.mesibo.messaging.MesiboConfiguration.MISSED_VOICE_CALL;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.RecyclerTouchListener;
import com.egongil.numva_android_app.src.numvatalk.NumvatalkActivity;
import com.mesibo.api.Mesibo;
import com.mesibo.api.MesiboProfile;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.MesiboUserListFragment;
import com.mesibo.messaging.UserData;
import com.mesibo.messaging.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NumvaTalkFragment extends Fragment implements Mesibo.MessageListener, Mesibo.ConnectionListener,
Mesibo.ProfileListener, Mesibo.SyncListener{
    public static final int MESIBO_INTITIAL_READ_USERLIST = 100 ;

//    private ArrayList<TalkListItem> mTalkList;
    private TalkListAdapter mAdapter;

    RecyclerView mRvTalkList;
    TextView mTvNoneGuide;

    //mesibo
    public long mForwardId = 0L;
    private int mTotalUnread = 0;
    private Mesibo.ReadDbSession mDbSession = null;
    private String mReadQuery = null;
    private WeakReference<MesiboUserListFragment.FragmentListener> mListener = null;

    private ArrayList<MesiboProfile> mUserProfiles = null;
    private ArrayList<MesiboProfile> mAdhocUserList = null;
    MesiboUI.Config mMesiboUIOptions = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numva_talk, container, false);

        //mesibo
        mMesiboUIOptions = MesiboUI.getConfig();
        this.mUserProfiles = new ArrayList<>();

        mReadQuery = null;

        Bundle b = this.getArguments();
        if(null != b) {
            mReadQuery = b.getString("query", null);
        }

        mRvTalkList = view.findViewById(R.id.numvatalk_rv_chatlist);
        mTvNoneGuide = view.findViewById(R.id.numvatalk_tv_guide_none);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvTalkList.setLayoutManager(manager);    //RecyclerView에 LayoutManager 등록
        mAdapter = new TalkListAdapter(getActivity(), this, mUserProfiles);
        mRvTalkList.setAdapter(mAdapter);   //RecyclerView에 Adapter 등록

        if(mUserProfiles.size() > 0){
            mRvTalkList.setVisibility(View.VISIBLE);
            mTvNoneGuide.setVisibility(View.GONE);
        }else{
            mRvTalkList.setVisibility(View.GONE);
            mTvNoneGuide.setVisibility(View.VISIBLE);
        }

        RecyclerTouchListener touchListener = new RecyclerTouchListener(getActivity(), mRvTalkList);
        touchListener.setSwipeOptionViews(R.id.numvatalk_rl_blockbtn, R.id.numvatalk_rl_exitbtn)
                .setSwipeable(R.id.numvatalk_ll_FG, R.id.numvatalk_ll_BG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch(viewID){
                            case R.id.numvatalk_rl_blockbtn:
                                //todo: 차단 동작
                                break;
                            case R.id.numvatalk_rl_exitbtn:
                                //todo: 나가기 동작
                                break;
                        }
                    }
                });
        mRvTalkList.addOnItemTouchListener(touchListener);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTalkList = new ArrayList<>();
//        //더미데이터
//        mTalkList.add(new TalkListItem(00, "라면먹는양수진", "안녕하세요",1));
//        mTalkList.add(new TalkListItem(01, "복싱싫은오혜린", "운동하기싫어yo",2));
    }

    public void setInitialLoginState() {
        if (sSharedPreferences.getString(X_ACCESS_TOKEN, "") == ""
        ||sSharedPreferences.getString(MESIBO_TOKEN, "")==""){
            setNonLoginState();
        }else{
            setLoginState();
        }
    }
    public void setNonLoginState(){
        mRvTalkList.setVisibility(View.GONE);
        mTvNoneGuide.setVisibility(View.VISIBLE);
    }
    public void setLoginState(){
        mRvTalkList.setVisibility(View.VISIBLE);
        mTvNoneGuide.setVisibility(View.GONE);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showUserList(int readCount){
        Log.d("showUserList", "showUserList");

        //setEmptyViewText();

        mTotalUnread = 0;
        Mesibo.addListener(this);
        mAdhocUserList = mUserProfiles;
        mUserProfiles.clear();
        mAdapter.onResumeAdapter();

        Mesibo.ReadDbSession.endAllSessions();


        mDbSession = new Mesibo.ReadDbSession(null, 0, mReadQuery, this);
        mDbSession.enableSummary(true);
        mDbSession.enableReadReceipt(false);

        int rcount = mDbSession.read(readCount);
        if(rcount<readCount){
            mDbSession.sync(readCount - rcount, this);
        }

        mUserProfiles.addAll(Mesibo.getSortedUserProfiles());

        if(mUserProfiles.size() > 0){
            mRvTalkList.setVisibility(View.VISIBLE);
            mTvNoneGuide.setVisibility(View.GONE);
        }else{
            mRvTalkList.setVisibility(View.GONE);
            mTvNoneGuide.setVisibility(View.VISIBLE);
        }

        mAdapter.notifyChangeInData();
    }

    private void updateContacts(MesiboProfile userProfile) {
        if(null == userProfile) {
            showUserList(MESIBO_INTITIAL_READ_USERLIST);
            return;
        }

        if(null == userProfile.other)
            return;

        UserData data = UserData.getUserData(userProfile);
        int position = data.getUserListPosition();
        if(position >= 0)
            mAdapter.notifyItemChanged(position);

        return;

    }
    //액티비티가 포커스될 때 호출됨
    @Override
    public void onResume() {
        super.onResume();

        showUserList(MESIBO_INTITIAL_READ_USERLIST);
        Mesibo_onConnectionStatus(Mesibo.getConnectionStatus());

        Utils.showServicesSuspendedAlert(getActivity());
    }

    @Override
    public void onPause(){
        super.onPause();

        Mesibo.removeListener(this);
    }
    @Override
    public void Mesibo_onConnectionStatus(int status) {
        //updateSubTitle이 기존 메시보 Ui모듈에서 메시보 액티비티의 텍스트를 바꾸는 함수였는데,
        // 넘바는 굳이 필요없지만 connectionStatus에 따라 다른 행동을 시켜야할수도있어서 일단 주석처리해둠
        if(status == Mesibo.STATUS_ONLINE){
//            updateSubTitle(mMesiboUIOptions.onlineIndicationTitle);
        }
        else if(status == Mesibo.STATUS_CONNECTING){
//            updateSubTitle(mMesiboUIOptions.connectingIndicationTitle);
        }
        else if(status == Mesibo.STATUS_SUSPENDED) {
//            updateSubTitle(mMesiboUIOptions.suspendIndicationTitle);
            Utils.showServicesSuspendedAlert(getActivity());
        }
        else if(status == Mesibo.STATUS_NONETWORK){
//            updateSubTitle(mMesiboUIOptions.noNetworkIndicationTitle);
        }
        else if(status == Mesibo.STATUS_SHUTDOWN) {
            getActivity().finish();
        }
        else{
//            updateSubTitle(mMesiboUIOptions.offlineIndicationTitle);
        }
    }

    private String appendNameToMessage(Mesibo.MessageParams params, String msg) {
        String name = params.peer;
        if (null != params.profile && null != params.profile.getName()) {
            name = params.profile.getName();
        }

        if (TextUtils.isEmpty(name)) {
            return msg;
        } else {
            String[] splited = name.split("\\s+");
            if (splited.length < 1) {
                return msg;
            } else {
                name = splited[0];
                if (name.length() > 12) {
                    name = name.substring(0, 12);
                }

                return name + ": " + msg;
            }
        }
    }

    private void updateUiIfLastMessage(Mesibo.MessageParams params) {
        if (params.isLastMessage()) {

            this.mAdapter.notifyChangeInData();
            //TODO: 메시지 왔을 때 noti배지
//            this.updateNotificationBadge();
        }
    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams params, byte[] data) {
        // This we will only get for real-time origin, we filter this in DB
        if(MSGSTATUS_CALLINCOMING == params.getStatus() || MSGSTATUS_CALLOUTGOING == params.getStatus()) {
            updateUiIfLastMessage(params); //?? required
            return true;
        }

        if(params.groupid > 0 && null == params.groupProfile) {
            updateUiIfLastMessage(params);
            return true;
        }

        String str = "";
        try {
            str = new String(data, "UTF-8");
        } catch (Exception e) {
            str = "";
        }

        if(params.isDeleted()) {
            str = MESSAGE_DELETED_STRING;
        }

        if(params.groupid > 0 && params.isIncoming()) {
            str = appendNameToMessage(params, str);
        }

        if(Mesibo.MSGSTATUS_CALLMISSED == params.getStatus()) {
            str = MISSED_VIDEO_CALL;
            if((params.getType()&1) == 0)
                str = MISSED_VOICE_CALL;
        }

        addNewMessage(params, str);
        updateUiIfLastMessage(params);
        return true;
    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams params) {
        if(params.isRealtimeMessage() && params.isMessageStatusInProgress()) return;

//        for(int i=0; i< mUserProfiles.size(); i++) {
//            UserData mcd = ((UserData)mUserProfiles.get(i).other);
//
//            if (mcd.getmid() != 0 && mcd.getmid() == params.mid) {
//                mcd.setStatus(params.getStatus());
//                if(params.isDeleted()) {
//                    mcd.setMessage(MESSAGE_DELETED_STRING);
//                    mcd.setDeletedMessage(true);
//                }
//                mAdapter.notifyItemChanged(i);
//            }
//        }
    }

    public void setListener(MesiboUserListFragment.FragmentListener listener) {
        this.mListener = new WeakReference(listener);
    }

    public MesiboUserListFragment.FragmentListener getListener() {
        return null == this.mListener ? null : (MesiboUserListFragment.FragmentListener)this.mListener.get();
    }

    public boolean onClickUser(String address, long groupid, long forwardid) {
        MesiboUserListFragment.FragmentListener l = this.getListener();
        return null == l ? false : l.Mesibo_onClickUser(address, groupid, forwardid);
    }

    public synchronized void addNewMessage(Mesibo.MessageParams params, String message) {
        if (params.groupid <= 0L || null != params.groupProfile) {
            MesiboUserListFragment.FragmentListener l = this.getListener();
            if (null == l || !l.Mesibo_onUserListFilter(params)) {
                UserData data = UserData.getUserData(params);
                MesiboProfile user;
                int i;

                user = params.profile;
                if (params.groupProfile != null) {
                    user = params.groupProfile;
                }

                if (null == user) {
                    Log.d("MesiboMainActivity", "Should not happen");
                }

                if (null == user.other) {
                    user.other = new UserData(user);
                }

                data = (UserData)user.other;
                data.setMessage(params.mid, this.getDate(params.ts), params.getStatus(), params.isDeleted(), message);
                this.mTotalUnread -= data.getUnreadCount();
                if (this.mTotalUnread < 0) {
                    this.mTotalUnread = 0;
                }

                if (Mesibo.isReading(params)) {
                    data.setUnreadCount(0);
                } else if (2 != params.origin && 1 != params.origin) {
                    data.setUnreadCount(data.getUnreadCount() + 1);
                } else {
                    data.setUnreadCount(user.unread);
                }

                this.mTotalUnread += data.getUnreadCount();
                if (0 == params.origin) {
                    //TODO: noti배지
//                    this.updateNotificationBadge();
                }

                for(i = 0; i < this.mAdhocUserList.size(); ++i) {
                    UserData mcd = (UserData)((MesiboProfile)this.mAdhocUserList.get(i)).other;
                    if (null != mcd && params.compare(mcd.getPeer(), mcd.getGroupId())) {
                        this.mAdhocUserList.remove(i);
                        break;
                    }
                }

                if (2 != params.origin && 1 != params.origin) {
                    this.mAdhocUserList.add(0, user);
                } else {
                    this.mAdhocUserList.add(user);
                }
            }
        }
        for(int i = 0; i<this.mUserProfiles.size(); ++i){
            this.mAdapter.notifyItemChanged(i);
        }
    }
    private String getDate(long time) {
        int days = Mesibo.daysElapsed(time);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = null;
        if (days == 0) {
            date = DateFormat.format("HH:mm", cal).toString();
        } else if (days == 1) {
            date = "Yesterday";
        } else if (days < 7) {
            date = DateFormat.format("E, dd MMM", cal).toString();
        } else {
            date = DateFormat.format("dd-MM-yyyy", cal).toString();
        }

        return date;
    }
    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams params, int activity) {
        if (3 == activity || 4 == activity || 11 == activity) {
            if (null != params && null != params.profile) {
                if (params.groupid <= 0L || null != params.groupProfile) {
                    MesiboProfile profile = params.profile;
                    if (params.groupid > 0L) {
                        profile = Mesibo.getProfile(params.groupid);
                        if (null == profile) {
                            return;
                        }
                    }

                    UserData data = UserData.getUserData(profile);
                    int position = data.getUserListPosition();
                    if (position >= 0) {
                        if (this.mAdhocUserList.size() > position) {
                            if (3 == activity && params.groupid > 0L) {
                                data.setTypingUser(params.profile);
                            }

                            try {
                                if (profile != this.mAdhocUserList.get(position)) {
                                    return;
                                }
                            } catch (Exception var7) {
                                return;
                            }

                            this.mAdapter.notifyItemChanged(position);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams params, Mesibo.Location location) {
        this.addNewMessage(params, "Location");
        this.updateUiIfLastMessage(params);
    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }

    @Override
    public void Mesibo_onProfileUpdated(MesiboProfile userProfile) {
        if (null == userProfile || null != userProfile.other) {
            if (Mesibo.isUiThread()) {
                this.updateContacts(userProfile);
            } else {
                (new Handler(Looper.getMainLooper())).post(new Runnable() {
                    public void run() {
                        NumvaTalkFragment.this.updateContacts(userProfile);
                    }
                });
            }
        }
    }

    @Override
    public boolean Mesibo_onGetProfile(MesiboProfile mesiboProfile) {
        return false;
    }

    @Override
    public void Mesibo_onSync(int count) {
        final int c = count;
        if(count > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showUserList(MESIBO_INTITIAL_READ_USERLIST);
                }
            });
        }
    }

    class TalkListAdapter extends RecyclerView.Adapter {
        Context mContext;
        NumvaTalkFragment mHost;
        ArrayList<MesiboProfile> mUsers;
        ArrayList<MesiboProfile> mDataList;


//        private ArrayList<TalkListItem> mTalkList = null;

//    public TalkListAdapter(ArrayList<TalkListItem> mTalkList) {
//        this.mTalkList = mTalkList;
//    }

        public TalkListAdapter(Context context, NumvaTalkFragment host, ArrayList<MesiboProfile> list) {

            this.mContext = context;
            mHost = host;
            mUsers = list;
            mDataList = list;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.item_rv_numvatalk_chatlist, parent, false);

            return new TalkListAdapter.TalkListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final MesiboProfile user = mDataList.get(position);

            UserData userdata = UserData.getUserData(user);
            userdata.setUser(user); // in case user is changed dynamically
            userdata.setUserListPosition(position);

            final UserData data = userdata;

            String strNickName = data.getUserName();
            String strLatestMessage = data.getLastMessage();
            int newMessageNum = data.getUnreadCount();

            //아이콘 - 랜덤컬러지정
            final GradientDrawable iconDrawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.circle_70dp);
            getRandomColor(iconDrawable);
            ((TalkListAdapter.TalkListViewHolder) holder).mIvIcon.setImageDrawable(iconDrawable);

            //닉네임, 최근메시지
            ((TalkListAdapter.TalkListViewHolder) holder).mTvNick.setText(strNickName);
            ((TalkListAdapter.TalkListViewHolder) holder).mTvLatestMessage.setText(strLatestMessage);

            //새 메시지 수
            if (newMessageNum > 0) {
                ((TalkListAdapter.TalkListViewHolder) holder).mTvMessageNum.setVisibility(View.VISIBLE);
                ((TalkListAdapter.TalkListViewHolder) holder).mTvMessageNum.setText(newMessageNum + "");
            } else {
                ((TalkListAdapter.TalkListViewHolder) holder).mTvMessageNum.setVisibility(View.GONE);
            }

            ((TalkListAdapter.TalkListViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userdata.clearUnreadCount();

                    boolean handledByApp = NumvaTalkFragment.this.onClickUser(user.address, user.groupid, NumvaTalkFragment.TalkListAdapter.this.mHost.mForwardId);
                    if (!handledByApp) {
//                        MesiboUIManager.launchMessagingActivity(NumvaTalkFragment.this.getActivity(), NumvaTalkFragment.TalkListAdapter.this.mHost.mForwardId, user.address, user.groupid);
                        Intent intent = new Intent(getActivity(), NumvatalkActivity.class);
                        intent.putExtra("peer", user.address);
                        intent.putExtra("groupid", user.groupid);
                        intent.putExtra("mid", NumvaTalkFragment.TalkListAdapter.this.mHost.mForwardId);
                        startActivity(intent);

                        NumvaTalkFragment.TalkListAdapter.this.mHost.mForwardId = 0L;
                    } else {
                        NumvaTalkFragment.TalkListAdapter.this.mHost.mForwardId = 0L;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public void updateData(ArrayList<MesiboProfile> mList) {
            mDataList.clear();
            mDataList.addAll(mList);
            notifyDataSetChanged();
        }

        public void onResumeAdapter() {
//        mSearchResult.clear();
//        mIsMessageSearching = false;
            mUsers.clear();
            mDataList = mUsers;
        }

        public void notifyChangeInData() {
//        mUiUpdateTimestamp = Mesibo.getTimestamp();
            mDataList = mUsers;
            notifyDataSetChanged();
        }

        public class TalkListViewHolder extends RecyclerView.ViewHolder {
            public View mView = null;
            TextView mTvNick, mTvLatestMessage, mTvMessageNum;
            ImageView mIvIcon;

            public TalkListViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
                mTvNick = itemView.findViewById(R.id.numvatalk_tv_nickname);
                mTvLatestMessage = itemView.findViewById(R.id.numvatalk_tv_recent_message);
                mTvMessageNum = itemView.findViewById(R.id.numvatalk_tv_new_message_num);
                mIvIcon = itemView.findViewById(R.id.numvatalk_iv_icon);
            }
        }
    }
}