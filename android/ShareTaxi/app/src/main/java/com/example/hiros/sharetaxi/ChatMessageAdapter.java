package com.example.hiros.sharetaxi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Hiros on 2018-06-08.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private static final int SYSTEM_VIEW = 0;
    private static final int SELF_VIEW = 1;
    private static final int RECEIVED_VIEW = 2;

    private Context mContext;
    private ArrayList<ChatMessage> mData;

    public ChatMessageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addItems(ChatMessage item) {
        if(mData == null) {
            mData = new ArrayList<>();
        }

        mData.add(item);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class SystemHolder extends ViewHolder {

        TextView messageTextView;

        public SystemHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView)itemView.findViewById(R.id.messageTextView);
        }
    }


    public class SelfHolder extends ViewHolder {

        TextView messageTextView;

        public SelfHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView)itemView.findViewById(R.id.messageTextView);
        }
    }

    public class ReceivedHolder extends ViewHolder {

        TextView messageTextView;
        TextView messageOwnerTextView;

        public ReceivedHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView)itemView.findViewById(R.id.messageTextView);
            messageOwnerTextView = (TextView)itemView.findViewById(R.id.messageOwnerTextView);
        }
    }

    public int getItemViewType(int position) {
        ChatMessage currentItem = mData.get(position);
        String messageType = currentItem.getMessageType();

        if(messageType.equals(Constants.MESSAGE_TYPE_SYSTEM)) {
            return SYSTEM_VIEW;
        } else if (messageType.equals(Constants.MESSAGE_TYPE_SELF)) {
            return SELF_VIEW;
        } else if (messageType.equals(Constants.MESSAGE_TYPE_RECEIVE)) {
            return RECEIVED_VIEW;
        }

        return super.getItemViewType(position);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;

        switch(viewType) {
            case SYSTEM_VIEW:
                view = inflater.inflate(R.layout.item_system_message, parent, false);
                return new SystemHolder(view);
            case SELF_VIEW:
                view = inflater.inflate(R.layout.item_self_message, parent, false);
                return new SelfHolder(view);
            case RECEIVED_VIEW:
                view = inflater.inflate(R.layout.item_recv_message, parent, false);
                return new ReceivedHolder(view);
            default:
                return null;
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        ChatMessage item = mData.get(position);

        if(viewType == SYSTEM_VIEW) {
            SystemHolder systemHolder = (SystemHolder)holder;

            String messageSuffix = "";

            if(item.getUserAction().equals("entered"))
                messageSuffix = "님이 채팅방에 들어왔습니다.";
            else if(item.getUserAction().equals("leaved"))
                messageSuffix =  "님이 채팅방을 나갔습니다.";

            systemHolder.messageTextView.setText(item.getMessageOwner() + messageSuffix);
        } else if(viewType == SELF_VIEW) {
            SelfHolder selfHolder = (SelfHolder)holder;

            selfHolder.messageTextView.setText(item.getMessageContent());
        } else if(viewType == RECEIVED_VIEW) {
            ReceivedHolder receivedHolder = (ReceivedHolder)holder;

            receivedHolder.messageOwnerTextView.setText(item.getMessageOwner());
            receivedHolder.messageTextView.setText(item.getMessageContent());
        }
    }

    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
