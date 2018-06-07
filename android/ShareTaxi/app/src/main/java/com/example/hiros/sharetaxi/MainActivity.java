package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity
                            implements OnTimePickerSetListener {

    EditText nknm;
    EditText start;
    EditText finish;
    EditText time;
    EditText y;
    EditText x;

    Button search;

    static final int START_REQUEST = 1;
    static final int FINISH_REQUEST = 2;

    sgtSocket mSocket = sgtSocket.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.bindListener(Constants.SHOW_ROOM, showRoom);

        nknm = (EditText)findViewById(R.id.edit_nknm);

        start = (EditText)findViewById(R.id.edit_start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.edit_start:
                        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                        try {
                            Intent intent = intentBuilder.build(MainActivity.this);
                            startActivityForResult(intent, START_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
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
                        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                        try {
                            Intent intent = intentBuilder.build(MainActivity.this);
                            startActivityForResult(intent, FINISH_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
/*
        //test
        Button  test = (Button)findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RateActivity.class);
                startActivity(intent);
            }
        });

*/
        time = (EditText)findViewById(R.id.edit_time);
        time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.edit_time:
                        TimePickerFragment mTimePickerFragment = new TimePickerFragment();
                        mTimePickerFragment.show(getSupportFragmentManager(), "temp");
                        break;
                }
            }
        });

        y = (EditText)findViewById(R.id.edit_y);
        x = (EditText)findViewById(R.id.edit_x);

        search = (Button)findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                // test code for RoomListActivity
                Intent intent = new Intent(MainActivity.this, RoomListActivity.class);
                startActivity(intent);
                */
                int id = v.getId();
                switch(id) {
                    case R.id.btn_search:
                        JSONObject sendData = new JSONObject();
                        try {
                            sendData.put("nknm", "USERNAME");
                            sendData.put("start", "START");
                            sendData.put("finish", "FINISH");
                            sendData.put("time", "TIME");
//                            sendData.put("y", Double.valueOf(y.getText().toString()));
//                            sendData.put("x", Double.valueOf(x.getText().toString()));
                            sendData.put("y", 0);
                            sendData.put("x", 0);
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
            if(requestCode == START_REQUEST)
            {
                final Place place = PlacePicker.getPlace(this, data);
                final CharSequence name = place.getName();
                start.setText(name);
            }
            else if(requestCode == FINISH_REQUEST)
            {
                final Place place = PlacePicker.getPlace(this, data);
                final CharSequence name = place.getName();
                finish.setText(name);
            }
        }
    }

    @Override
    public void onTimePickerSet(int hour, int min)
    {
        if(hour <= 12)
            time.setText(hour + "시 " + min + "분 AM");
        else
            time.setText(hour-12 + "시 " + min + "분 PM");
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
