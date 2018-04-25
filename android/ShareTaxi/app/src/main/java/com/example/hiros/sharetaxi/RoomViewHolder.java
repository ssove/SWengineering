package com.example.hiros.sharetaxi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Hiros on 2018-04-25.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {

    public TextView master, start, finish, time, numUser;
    public OnRoomClickListener mListener;

    public RoomViewHolder(View itemView) {
        super(itemView);
        master = (TextView)itemView.findViewById(R.id.item_room_master);
        start = (TextView)itemView.findViewById(R.id.item_room_start);
        finish = (TextView)itemView.findViewById(R.id.item_room_finish);
        time = (TextView)itemView.findViewById(R.id.item_room_time);
        numUser = (TextView)itemView.findViewById(R.id.item_room_numUser);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRoomClickListener(getAdapterPosition());
            }
        });
    }

    public void setOnRoomClickListener(OnRoomClickListener onRoomClickListener) {
        mListener = onRoomClickListener;
    }
}
