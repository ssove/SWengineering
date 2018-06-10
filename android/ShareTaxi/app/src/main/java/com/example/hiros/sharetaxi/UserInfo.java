package com.example.hiros.sharetaxi;

/**
 * Created by Hiros on 2018-04-24.
 */

public class UserInfo {

    private volatile static UserInfo instance;

    public double y;
    public double x;
    public String start;
    public String finish;
    public String nknm;

    public static UserInfo getInstance() {
        if(instance == null) {
            synchronized (sgtSocket.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    private UserInfo() {
        nknm = "TEMPUSER";
        y = 0;
        x = 0;
        start = "출발지";
        finish = "도착지";
    }
}
