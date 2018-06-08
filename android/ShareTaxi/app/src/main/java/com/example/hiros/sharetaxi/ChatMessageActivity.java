package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

/**
 * Created by Hiros on 2018-06-08.
 */

public class ChatMessageActivity extends AppCompatActivity{

    private ChatMessageAdapter mAdapter;
    private RecyclerView messageView;
    private EditText messageEditText;
    private Button sendButton;

    private int enableTextColor = Color.parseColor("#523738");
    private int disableTextColor = Color.parseColor("#D7D7D7");

    sgtSocket mSocket = sgtSocket.getInstance();
    UserInfo userInfo = UserInfo.getInstance();

    private String rid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageView = (RecyclerView)findViewById(R.id.messageView);
        messageEditText = (EditText)findViewById(R.id.messageEditText);
        sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(sendButtonTouchUp);

        Intent intent = new Intent(this.getIntent());
        Bundle bundle = intent.getExtras();
        rid = bundle.getString("rid");

        Log.d("CHATVIEW", userInfo.nknm);
        initializeView();
        setupRecyclerView();

        mSocket.bindListener(Constants.NEW_MESSAGE, onMessageReceived);
        joinRoom();
    }

    private void initializeView() {
        getSupportActionBar().setTitle("채팅방");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendButton.setEnabled(false);
        sendButton.setTextColor(disableTextColor);
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    sendButton.setEnabled(true);
                    sendButton.setTextColor(enableTextColor);
                } else {
                    sendButton.setEnabled(false);
                    sendButton.setTextColor(disableTextColor);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupRecyclerView() {
        mAdapter = new ChatMessageAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messageView.setHasFixedSize(true);
        messageView.setLayoutManager(layoutManager);
        messageView.setAdapter(mAdapter);

        messageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int newLeft, int newTop, int newRight, int newBottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(newBottom < oldBottom) {
                    messageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messageView.smoothScrollToPosition(mAdapter.getItemCount());
                        }
                    }, 100);
                }
            }
        });
    }

    private Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject rcvData = (JSONObject)args[0];
            String userAction = rcvData.optString("action");
            String messageOwner = rcvData.optString("nknm");
            String messageContent = rcvData.optString("message");
            String messageType = Constants.MESSAGE_TYPE_RECEIVE;
            if(userAction.equals("entered")) {
                messageType = Constants.MESSAGE_TYPE_SYSTEM;
            } else if(messageOwner.equals(userInfo.nknm)) {
                messageType = Constants.MESSAGE_TYPE_SELF;
            }

            final ChatMessage message = new ChatMessage(userAction, messageType, messageOwner, messageContent);
            Log.d("CHATVIEW", "action: " + message.getUserAction());
            Log.d("CHATVIEW", "type: " + message.getMessageType());
            Log.d("CHATVIEW", "owner: " + message.getMessageOwner());
            Log.d("CHATVIEW", "message: " + message.getMessageContent());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addItems(message);
                    messageView.smoothScrollToPosition(mAdapter.getItemCount());
                }
            });
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    View.OnClickListener sendButtonTouchUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String sendMessage = messageEditText.getText().toString();
            Log.d("CHATVIEW", "send: " + sendMessage);

            JSONObject sendData = new JSONObject();

            try {
                sendData.put("nknm", userInfo.nknm);
                sendData.put("message", sendMessage);
                sendData.put("rid", rid);
                mSocket.emit(Constants.NEW_MESSAGE, sendData);

                messageEditText.setText(null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void joinRoom() {
        JSONObject sendData = new JSONObject();

        try {
            sendData.put("nknm", userInfo.nknm);
            sendData.put("rid", rid);
            mSocket.emit(Constants.JOIN_ROOM, sendData);

            messageEditText.setText(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
