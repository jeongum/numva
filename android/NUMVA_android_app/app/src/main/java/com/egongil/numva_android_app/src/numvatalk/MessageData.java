package com.egongil.numva_android_app.src.numvatalk;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboImages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MessageData {
    private String mUserName = null;
    private String mTimeStamp = null;
    private String mDateStamp = null;
    private String mPrintDateStamp = null;
    private long mTimestampMs = 0L;
    private Boolean mPNG = false;
    private long mGroupId = 0L;
    private int mType = 0;
    private boolean mFavourite = false;
    private String mPeer = null;
    private boolean mIsReply = false;
    private String mReplyString = null;
    private Bitmap mReplyBitmap = null;
    private String mReplyName = null;
    private boolean mShowName = true;
    public static final int MESSAGEDATA_TYPE_MESSAGE = 1;
    public static final int MESSAGEDATA_TYPE_DATE = 2;
    public static final int MESSAGEDATA_TYPE_CUSTOM = 3;
    private MesiboRecycleViewHolder mViewHolder = null;
    private int mNameColor = -8947849;
    private Mesibo.MessageParams mParams = null;
    private Mesibo.MesiboMessage mMsg = null;
    private boolean locationImageRequested = false;
    private boolean mDeleted = false;
    private Mesibo.MessageListener mMessageListener = null;

    MessageData(Mesibo.MessageParams params, long mid, String peer, String username, String message, long ts, int status, long gid) {
        this.mMsg = new Mesibo.MesiboMessage();
        this.mMsg.message = message;
        this.mMsg.status = status;
        this.mMsg.mid = mid;
        this.mMsg.type = params.type;
        this.mUserName = username;
        this.mPNG = false;
        this.mGroupId = gid;
        this.mPeer = peer;
        this.mParams = params;
        this.mDeleted = params.isDeleted();
        this.mType = 1;
        if (0L == ts) {
            ts = Mesibo.getTimestamp();
        }

        this.mTimestampMs = ts;
        this.mMsg.ts = ts;
        this.setTimestamps();
    }

    MessageData(int type, long ts) {
        this.mType = type;
        this.mTimeStamp = null;
        if (0L == ts) {
            ts = Mesibo.getTimestamp();
        }

        this.mTimestampMs = ts;
        this.setTimestamps();
    }

    void setParams(Mesibo.MessageParams params) {
        this.mParams = params;
    }

    Mesibo.MessageParams getParams() {
        return this.mParams;
    }

    void setMessageListener(Mesibo.MessageListener listener) {
        this.mMessageListener = listener;
    }

    void setViewHolder(MesiboRecycleViewHolder vh) {
        MesiboRecycleViewHolder pv = this.mViewHolder;
        this.mViewHolder = null;
        if (null != pv) {
            pv.reset();
        }

        this.mViewHolder = vh;
        if (null != vh && null != this.mMsg && null != this.mMsg.location && null == this.mMsg.location.image && this.mMessageListener != null && !this.locationImageRequested) {
            this.locationImageRequested = true;
            Mesibo.updateLocationImage(this.mParams, this.mMsg.location, this.mMessageListener);
        }

    }

    public Mesibo.MesiboMessage getMesiboMessage() {
        return this.mMsg;
    }

    public int getPosition() {
        return null != this.mViewHolder ? this.mViewHolder.getItemPosition() : -1;
    }

    MesiboRecycleViewHolder getViewHolder() {
        return this.mViewHolder;
    }

    public Mesibo.FileInfo getFile() {
        return this.mMsg.file;
    }

    public void setFile(Mesibo.FileInfo file) {
        this.mMsg.file = file;
        if (null != this.mMsg.file) {
            this.mMsg.file.setData(this);
        }

    }

    public void setLocation(Mesibo.Location location) {
        this.mMsg.location = location;
        if (null != this.mMsg.location) {
            this.mMsg.location.setData(this);
        }

    }

    private void setTimestamps() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.mTimestampMs);
        Date currtime = calendar.getTime();
        this.mTimeStamp = null;
        this.mDateStamp = null;
        SimpleDateFormat sdf;
        if (1 == this.mType) {
            sdf = new SimpleDateFormat("HH:mm");
            this.mTimeStamp = sdf.format(currtime);
        }

        sdf = new SimpleDateFormat("dd/MM/yy");
        this.mDateStamp = sdf.format(currtime);
        this.mPrintDateStamp = this.mDateStamp;
        int days = Mesibo.daysElapsed(this.mTimestampMs);
        if (0 == days) {
            this.mPrintDateStamp = "Today";
        } else if (1 == days) {
            this.mPrintDateStamp = "Yesterday";
        } else if (days < 7) {
            sdf = new SimpleDateFormat("E, dd MMM");
            this.mPrintDateStamp = sdf.format(currtime);
        }

    }

    public boolean isImageVideo() {
        if (null != this.mMsg && null != this.mMsg.file) {
            return this.mMsg.file.type == 1 || this.mMsg.file.type == 2;
        } else {
            return false;
        }
    }

    public boolean isLocation() {
        if (null == this.mMsg) {
            return false;
        } else {
            return null != this.mMsg.location;
        }
    }

    public long getGroupId() {
        return this.mGroupId;
    }

    public String getPeer() {
        return this.mPeer;
    }

    public String getUsername() {
        return TextUtils.isEmpty(this.mUserName) ? this.getPeer() : this.mUserName;
    }

    public long getMid() {
        return null == this.mMsg ? -1L : this.mMsg.mid;
    }

    public int getMessageType() {
        return null == this.mMsg ? -1 : this.mMsg.type;
    }

    public Mesibo.Location getLocation() {
        return this.mMsg.location;
    }

    public String getTitle() {
        if (null == this.mMsg) {
            return null;
        } else if (null != this.mMsg.file) {
            return this.mMsg.file.title;
        } else {
            return null != this.mMsg.location ? this.mMsg.location.title : null;
        }
    }

    public String getMessage() {
        if (null == this.mMsg) {
            return null;
        } else {
            return this.isDeleted() ? "This message was deleted" : this.mMsg.message;
        }
    }

    public Boolean getMPNG() {
        return this.mPNG;
    }

    public String getTimestamp() {
        return this.mTimeStamp;
    }

    public long getTimestampMs() {
        return this.mTimestampMs;
    }

    public int getStatus() {
        return null == this.mMsg ? -1 : this.mMsg.status;
    }

    public boolean isDeleted() {
        return this.mDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.mDeleted = deleted;
    }

    public String getUrl() {
        return null != this.mMsg && null != this.mMsg.file ? this.mMsg.file.getUrl() : null;
    }

    public Bitmap getImage() {
        if (null == this.mMsg) {
            return null;
        } else if (null != this.mMsg.file) {
            return this.mMsg.file.image;
        } else if (null != this.mMsg.location) {
            return null != this.mMsg.location.image ? this.mMsg.location.image : MesiboImages.getDefaultLocationBitmap();
        } else {
            return null;
        }
    }

    public boolean hasImage() {
        if (null == this.mMsg) {
            return false;
        } else {
            return null != this.mMsg.file || null != this.mMsg.location;
        }
    }

    public boolean isFileTransferred() {
        return null != this.mMsg && null != this.mMsg.file ? this.mMsg.file.isTransferred() : false;
    }

    public String getDateStamp() {
        return this.mDateStamp;
    }

    public String getPrintDateStamp() {
        return this.mPrintDateStamp;
    }

    public void setStaus(int status) {
        if (null != this.mMsg) {
            this.mMsg.status = status;
        }

    }

    public int getType() {
        return this.mType;
    }

    public void setFavourite(Boolean favourite) {
        this.mFavourite = favourite;
    }

    public Boolean getFavourite() {
        return this.mFavourite;
    }

    public boolean getReplyStatus() {
        return this.mIsReply;
    }

    public void setReplyStatus(boolean mIsReply) {
        this.mIsReply = mIsReply;
    }

    public String getReplyString() {
        return this.mReplyString;
    }

    public void setReplyString(String mReplyString) {
        this.mReplyString = mReplyString;
    }

    public Bitmap getReplyBitmap() {
        return this.mReplyBitmap;
    }

    public void setReplyBitmap(Bitmap mReplyBitmap) {
        this.mReplyBitmap = mReplyBitmap;
    }

    public String getReplyName() {
        return this.mReplyName;
    }

    public void setReplyName(String mReplyName) {
        this.mReplyName = mReplyName;
    }

    public void setNameColor(int color) {
        this.mNameColor = color;
    }

    public int getNameColor() {
        return this.mNameColor;
    }

    public void checkPreviousData(com.mesibo.messaging.MessageData pd) {
        if (!this.hasImage() && !pd.hasImage()) {
            String prevPeer = pd.getPeer();
            if (null != prevPeer && null != this.mPeer && prevPeer.equalsIgnoreCase(this.mPeer)) {
                this.mShowName = false;
            }

        } else {
            this.mShowName = true;
        }
    }

    public boolean isShowName() {
        return this.mShowName;
    }
}
