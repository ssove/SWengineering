package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        start = (EditText)findViewById(R.id.edit_start);
        finish = (EditText)findViewById(R.id.edit_finish);
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
