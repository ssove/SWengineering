package com.example.hiros.sharetaxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import org.json.JSONException;
import org.json.JSONObject;

public class RateActivity extends AppCompatActivity {
    private static final int USERCNT = 3;
    private RadioGroup[] rateOfUser;
    private UserInfo[] userInfo;
    sgtSocket mSocket = sgtSocket.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Button buttonRate = (Button) findViewById(R.id.bt_finish);
        userInfo = new UserInfo[USERCNT]; // 수정 필요
        rateOfUser[0] = (RadioGroup) findViewById(R.id.radio_grp1);
        rateOfUser[1] = (RadioGroup) findViewById(R.id.radio_grp2);
        rateOfUser[2] = (RadioGroup) findViewById(R.id.radio_grp3);

        buttonRate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < USERCNT; i++) {
                    RadioButton checkedButton = (RadioButton) findViewById(rateOfUser[i].getCheckedRadioButtonId());
                    String buttonText = checkedButton.getText().toString();

                    if (buttonText.equals("좋아요"))
                        userInfo[i].score++;
                    else if (buttonText.equals("싫어요"))
                        userInfo[i].score--;

                    /* 서버로 수정된 유저정보 전송 */
                    JSONObject sendData = new JSONObject();
                    try {
                        sendData.put("nknm", "USERNAME");
                        sendData.put("score", userInfo[i].score);
                        mSocket.emit("update userInfo", sendData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
