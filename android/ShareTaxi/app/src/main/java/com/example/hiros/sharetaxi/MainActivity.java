package com.example.hiros.sharetaxi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity{

    EditText nknm;
    EditText start;
    EditText finish;
    EditText time;
    EditText y;
    EditText x;

    Button search;

    sgtSocket mSocket = sgtSocket.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.bindListener(Constants.SHOW_ROOM, showRoom);

        nknm = (EditText)findViewById(R.id.edit_nknm);

        // 사용자가 start칸이든 finish칸이든 터치하면 Intent(main activity, 지도 activity)
        // 지도 activity는 내 위치를 보여주면서 시작
        // 장소가 입력되면
        // Intent(지도 activity, main activity)
        // 출발지든 도착지든 장소 문자열 받아와서 main activity에 putExtra(intent) 단, 이게 putExtra의 올바른 용례인지는 조사가 필요

        start = (EditText)findViewById(R.id.edit_start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.edit_start:
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        //intent.putExtra("nickName", nknm.getText().toString());
                        startActivityForResult(intent, 1);

                        break;
                }
            }
        });

        finish = (EditText)findViewById(R.id.edit_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.edit_finish:
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        //intent.putExtra("nickName", nknm.getText().toString());
                        startActivityForResult(intent, 2);
                        break;
                }
            }
        });

        time = (EditText)findViewById(R.id.edit_time);
        y = (EditText)findViewById(R.id.edit_y);
        x = (EditText)findViewById(R.id.edit_x);

        search = (Button)findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.btn_search:
                        JSONObject sendData = new JSONObject();
                        try {
                            sendData.put("nknm", "USERNAME");
                            sendData.put("start", "START");
                            sendData.put("finish", "FINISH");
                            sendData.put("time", "TIME");
                            sendData.put("y", Double.valueOf(y.getText().toString()));
                            sendData.put("x", Double.valueOf(x.getText().toString()));
                            mSocket.emit("show rooms", sendData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                start.setText(data.getExtras().getString("placeName"));
            }
            else if(requestCode == 2)
            {
                finish.setText(data.getExtras().getString("placeName"));
            }
        }
    }
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//
//        SharedPreferences sp = this.getSharedPreferences("save", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        editor.putString("nknm", nknm.getText().toString());
//        editor.commit();
//    }

//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//
//        SharedPreferences sp = this.getSharedPreferences("save", Context.MODE_PRIVATE);
//        String restoredText = sp.getString("text", null);
//        if(restoredText != null) {
//            nknm.setText(sp.getString("nknm", null));
//        }
//
//        Intent intent = getIntent();
//        if(intent != null) {
//            String name = intent.getExtras().getString("place");
//            String locationType = intent.getExtras().getString("locationType");
//
//            if (locationType == "start")
//                start.setText(name);
//            else if (locationType == "finish")
//                finish.setText(name);
//        }
//    }
    /**
     * show rooms Listener
     */
    private Emitter.Listener showRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 서버로 전송할 데이터 생성 및 채널 입장 이벤트 보냄.

            final JSONObject rcvData = (JSONObject) args[0];

            Intent intent = new Intent(MainActivity.this, RoomListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("rooms", rcvData.optString("rooms"));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}
