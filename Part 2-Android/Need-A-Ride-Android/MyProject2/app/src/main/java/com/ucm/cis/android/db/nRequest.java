package com.ucm.cis.android.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LekshmiPriya on 4/30/2016.
 */
public class nRequest extends Ride implements Serializable{

    //@Id
    private String requestId;
    private boolean active;
    private Date datetime;

    private nLatLong source;

    private nLatLong destination;

    //private String time;
    private String userName;



    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public nLatLong getSource() {
        return source;
    }
    public void setSource(nLatLong source) {
        this.source = source;
    }
    public nLatLong getDestination() {
        return destination;
    }
    public void setDestination(nLatLong destination) {
        this.destination = destination;
    }

    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean isActive) {
        this.active = isActive;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
