package com.example.hiros.sharetaxi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiros on 2018-04-25.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder> implements OnRoomClickListener {

    List<RoomInfo> items = new ArrayList<>();

    Context mContext;

    public RoomAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void add(RoomInfo room) {
        items.add(room);
        notifyDataSetChanged();
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, null);
        RoomViewHolder holder = new RoomViewHolder(v);
        holder.setOnRoomClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        RoomInfo room = items.get(position);
        Log.d("RoomAdapter", room.toString());
        holder.master.setText(room.getMaster());
        holder.start.setText(room.getStart());
        holder.finish.setText(room.getFinish());
        holder.time.setText(room.getTime());
        holder.numUser.setText(room.getNumUsers());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onRoomClickListener(int position) {
        Toast.makeText(mContext, items.get(position).toString(), Toast.LENGTH_SHORT).show();
    }
}
