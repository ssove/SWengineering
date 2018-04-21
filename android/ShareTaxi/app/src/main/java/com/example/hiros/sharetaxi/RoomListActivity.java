package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RoomListActivity extends AppCompatActivity {

    private static String TAG = "RoomList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Intent intent = new Intent(this.getIntent());
        Bundle bundle = intent.getExtras();
        String nknm = bundle.getString("nknm");
        String start = bundle.getString("start");
        String finish = bundle.getString("finish");
        String time = bundle.getString("time");

        Log.d(TAG, nknm + " " + start + " " + finish + " " + time);
    }
}