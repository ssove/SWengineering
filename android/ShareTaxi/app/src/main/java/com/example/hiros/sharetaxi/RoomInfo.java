package com.example.hiros.sharetaxi;

/**
 * Created by Hiros on 2018-04-25.
 */

public class RoomInfo {
    private String rid;
    private String master;
    private String start;
    private String finish;
    private String time;
    private String numUsers;

    public RoomInfo() {

    }

    public RoomInfo(String rid, String master, String start, String finish, String time, String numUsers) {
        this.rid = rid;
        this.master = master;
        this.start = start;
        this.finish = finish;
        this.time = time;
        this.numUsers = numUsers;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumUsers(String numUsers) {
        this.numUsers = numUsers;
    }

    public String getRid() {
        return rid;
    }

    public String getMaster() {
        return master;
    }

    public String getStart() {
        return start;
    }

    public String getFinish() {
        return finish;
    }

    public String getTime() {
        return time;
    }

    public String getNumUsers() {
        return numUsers;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "rid='" + rid + '\'' +
                "master='" + master + '\'' +
                ", start='" + start + '\'' +
                ", finish='" + finish + '\'' +
                ", time='" + time + '\'' +
                ", numUsers=" + numUsers +
                '}';
    }
}
