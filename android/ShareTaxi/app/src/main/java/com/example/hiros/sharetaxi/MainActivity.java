package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    EditText nknm;
    EditText start;
    EditText finish;
    EditText time;

    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nknm = (EditText)findViewById(R.id.edit_nknm);
        start = (EditText)findViewById(R.id.edit_start);
        finish = (EditText)findViewById(R.id.edit_finish);
        time = (EditText)findViewById(R.id.edit_time);

        search = (Button)findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch(id) {
                    case R.id.btn_search:
                        Intent intent = new Intent(MainActivity.this, RoomListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("nknm", nknm.getText().toString());
                        bundle.putString("start", start.getText().toString());
                        bundle.putString("finish", finish.getText().toString());
                        bundle.putString("time", time.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
