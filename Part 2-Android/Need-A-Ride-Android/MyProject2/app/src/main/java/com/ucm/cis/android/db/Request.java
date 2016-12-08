package com.ucm.cis.android.db;

/**
 * Created by LekshmiPriya on 4/21/2015.
 */

//package com.lpg.model;

import java.io.Serializable;

public class Request implements Serializable{

    private String source;
    private String destination;
    private LatLong slatLong;
    private LatLong dlatLong;
    private String date;
    private String time;
    private String userName;
    private int id;
    private boolean isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLong getSlatLong() {
        return slatLong;
    }

    public void setSlatLong(LatLong slatLong) {
        this.slatLong = slatLong;
    }

    public LatLong getDlatLong() {
        return dlatLong;
    }

    public void setDlatLong(LatLong dlatLong) {
        this.dlatLong = dlatLong;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActive() {

        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
