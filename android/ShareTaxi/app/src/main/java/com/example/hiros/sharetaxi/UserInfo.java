package com.example.hiros.sharetaxi;

/**
 * Created by Hiros on 2018-04-24.
 */

public class UserInfo {

    private volatile static UserInfo instance;

    public int score;
    public double y;
    public double x;
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
        score = 0;
        y = 0;
        x = 0;
        nknm = "TEMPUSER";
    }
}
