//package com.lpg.model;

package com.ucm.cis.android.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by LekshmiPriya on 4/21/2015.
 */
public class Offer implements Serializable {

    private String source;
    private String destination;
    private String date;
    private String time;
    private LatLong slatLong;
    private LatLong dlatLong;
    private int seats;
    private float distance;
    private String registration;
    private String userName;
    private int id;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
       /* SimpleDateFormat sdf = new SimpleDateFormat();
        String date1 = sdf.format(date);
        */return date;
    }

    public void setDate(String strdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
        /*Date date2= new Date();
        try {
             date2 = sdf.for(strdate);
        } catch (Exception e) {
            System.out.println("Error occured");
        }*/
        this.date = strdate;
    }

    public String getTime() {
       /* SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time1=sdf.format(time);*/
        return time;
    }

    public void setTime(String strTime) {
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date time2=null;
        try {
            time2 = sdf.parse(strTime);
        } catch (Exception e) {
            System.out.println("Error occured");
        }*/
        this.time = strTime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

}
