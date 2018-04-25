package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomListActivity extends AppCompatActivity {

    private static String TAG = "RoomList";

    RoomAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        mAdapter = new RoomAdapter(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_room_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = new Intent(this.getIntent());
        Bundle bundle = intent.getExtras();
        String rooms = bundle.getString("rooms");

        JSONArray jsonArray;
        Log.d(TAG, rooms);
        try {
            jsonArray = new JSONArray(rooms);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                RoomInfo room = new RoomInfo(jsonObject.optString("rid"), "master", "start", "finish", "time", jsonObject.optString("numUsers"));
//                room.setMaster(jsonObject.optString("master"));
//                room.setStart(jsonObject.optString("start"));
//                room.setFinish(jsonObject.optString("finish"));
//                room.setTime(jsonObject.optString("time"));
//                room.setNumUsers(Integer.valueOf(jsonObject.optString("numUsers")));

                mAdapter.add(room);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}