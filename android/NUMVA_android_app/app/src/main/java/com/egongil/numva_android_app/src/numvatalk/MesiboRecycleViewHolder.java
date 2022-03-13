package com.egongil.numva_android_app.src.numvatalk;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MessageAdapter;

public class MesiboRecycleViewHolder extends RecyclerView.ViewHolder {
    public static final int TYPE_NONE = 0;
    public static final int TYPE_INCOMING = 1;
    public static final int TYPE_OUTGOING = 2;
    public static final int TYPE_DATETIME = 3;
    public static final int TYPE_HEADER = 4;
    public static final int TYPE_MISSEDCALL = 5;
    public static final int TYPE_CUSTOM = 100;
    private int mType = 0;
    private int mPosition = -1;
    private boolean mCustom = false;
    private MessageAdapter mAdapter = null;

    public void reset() {
    }

    public MesiboRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected int getItemPosition() {
        return this.mPosition;
    }

    protected void setItemPosition(int pos) {
        this.mPosition = pos;
    }

    public int getType() {
        return this.mType;
    }

    public void refresh() {
        if (null != this.mAdapter && this.mPosition > 0) {
            this.mAdapter.notifyItemChanged(this.mPosition);
        }

    }

    public void delete(int type) {
        if (null != this.mAdapter && this.mPosition > 0) {
            this.mAdapter.removeFromChatList(this.mPosition, type);
        }

    }

    protected void setAdapter(MessageAdapter adapter) {
        this.mAdapter = adapter;
    }

    protected void setType(int type) {
        this.mType = type;
    }

    protected void setCustom(boolean custom) {
        this.mCustom = custom;
    }

    protected boolean getCustom() {
        return this.mCustom;
    }

    public static class MesiboViewData {
        int screenType;
        int viewType;
        boolean selected;
        Mesibo.MessageParams params;
        String message;
        String activityStatus;

        public MesiboViewData() {
        }
    }

    public interface Listener {
        int Mesibo_onGetItemViewType(Mesibo.MessageParams params, String message);

        com.mesibo.messaging.MesiboRecycleViewHolder Mesibo_onCreateViewHolder(ViewGroup viewGroup, int viewType);

        void Mesibo_onBindViewHolder(com.mesibo.messaging.MesiboRecycleViewHolder holder, int viewType, boolean selected, Mesibo.MessageParams params, Mesibo.MesiboMessage message);

        void Mesibo_oUpdateViewHolder(com.mesibo.messaging.MesiboRecycleViewHolder holder, Mesibo.MesiboMessage message);

        void Mesibo_onViewRecycled(com.mesibo.messaging.MesiboRecycleViewHolder holder);
    }
}
