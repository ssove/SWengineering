package com.example.hiros.sharetaxi;

import android.util.Log;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Hiros on 2018-04-21.
 */

public class sgtSocket {

    private volatile static sgtSocket instance;
    public Socket mSocket;
    private final String TAG = "Socket";

    public static sgtSocket getInstance() {
        if(instance == null) { //인스턴스가 있는지 확인
            synchronized (sgtSocket.class) { //없으면 동기화
                if (instance == null) { //다시 확인
                    instance = new sgtSocket(); //생성
                }
            }
        }
        return instance;
    }

    private sgtSocket() {
        try {
            mSocket = IO.socket(Constants.SOCKET_URL);
            mSocket.connect();
            Log.d(TAG, "connected");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void bindListener(String event, Emitter.Listener listener) {
        mSocket.on(event, listener);
    }

    public void emit(String event, JSONObject jsonObject) {
        mSocket.emit(event, jsonObject);
    }
}
