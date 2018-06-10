package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class RoomListActivity extends AppCompatActivity {

    private static String TAG = "RoomList";

    RoomAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    Toolbar mToolbar;

    sgtSocket mSocket = sgtSocket.getInstance();
    UserInfo userInfo = UserInfo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        mAdapter = new RoomAdapter(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_room_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_wait);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mSocket.bindListener(Constants.SHOW_ROOM, showRoom);
        mSocket.bindListener(Constants.GET_ROOMID, getRoomId);
        //test data
/*
        RoomInfo room = new RoomInfo("rid","곽의 방", "경북대학교", "대구역", "17:00", "1/4");
        mAdapter.add(room);
        RoomInfo room1 = new RoomInfo("rid","리의 방", "경북대학교", "내 집", "17:00", "1/4");
        mAdapter.add(room1);
        RoomInfo room2 = new RoomInfo("rid","차의 방", "공대9호관", "협동관", "17:00", "1/4");
        mAdapter.add(room2);
        RoomInfo room3 = new RoomInfo("rid","황의 방", "경북대학교", "대구역", "17:00", "1/4");
        mAdapter.add(room3);
        RoomInfo room4 = new RoomInfo("rid","우의 방", "경북대학교", "대구역", "17:00", "1/4");
        mAdapter.add(room4);
        RoomInfo room6 = new RoomInfo("rid","tester", "경북대학교", "대구역", "17:00", "1/4");
        mAdapter.add(room6);
        */

        JSONObject sendData = new JSONObject();
        try {
            sendData.put("nknm", userInfo.nknm);
            sendData.put("start", userInfo.start);
            sendData.put("finish", userInfo.finish);
            sendData.put("time", "TIME");
//                            sendData.put("y", Double.valueOf(y.getText().toString()));
//                            sendData.put("x", Double.valueOf(x.getText().toString()));
            sendData.put("y", userInfo.y);
            sendData.put("x", userInfo.x);
            mSocket.emit("show rooms", sendData);
            Log.d("LISTVIEW", sendData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * show rooms Listener
     */
    private Emitter.Listener showRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 서버로 전송할 데이터 생성 및 채널 입장 이벤트 보냄.

            final JSONObject rcvData = (JSONObject) args[0];

            String rooms = rcvData.optString("rooms");

            JSONArray jsonArray;

            try {
                jsonArray = new JSONArray(rooms);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    RoomInfo room = new RoomInfo();
//                    RoomInfo room = new RoomInfo(jsonObject.optString("rid"), "master", "start", "finish", "time", jsonObject.optString("numUsers"));
                    room.setRid(jsonObject.optString("rid"));
                    room.setMaster(jsonObject.optString("master"));
                    room.setStart(jsonObject.optString("start"));
                    room.setFinish(jsonObject.optString("finish"));
                    room.setTime(jsonObject.optString("time"));
                    room.setNumUsers(jsonObject.optString("numUsers"));

                    mAdapter.add(room);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void makeRoom() {
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("nknm", userInfo.nknm);
            sendData.put("start", userInfo.start);
            sendData.put("finish", userInfo.finish);
            sendData.put("time", "TIME");
//                            sendData.put("y", Double.valueOf(y.getText().toString()));
//                            sendData.put("x", Double.valueOf(x.getText().toString()));
            sendData.put("y", userInfo.y);
            sendData.put("x", userInfo.x);
            mSocket.emit("make room", sendData);
            Log.d("LISTVIEW", sendData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener getRoomId = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 서버로 전송할 데이터 생성 및 채널 입장 이벤트 보냄.

            final JSONObject rcvData = (JSONObject) args[0];

            String id = rcvData.optString("id");

            Intent intent = new Intent(RoomListActivity.this, ChatMessageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("rid", id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_wait, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.make_room:
                makeRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}