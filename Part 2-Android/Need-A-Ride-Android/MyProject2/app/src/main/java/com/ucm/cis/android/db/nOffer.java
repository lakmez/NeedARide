package com.ucm.cis.android.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LekshmiPriya on 4/30/2016.
 */
public class nOffer extends Ride implements Serializable{


    //@Id
    private String offerid;
    private int seats;


    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    private float distance;
    private nLatLong source;
    private nLatLong destination;
    private String registration;
    private Date datetime;
    private List<String> riderIds;
    private String userName;


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    public void addRider(String rider) {
        riderIds.add(rider);
    }
    public Date getDatetime() {
        return datetime;
    }



    public nLatLong getDestination() {
        return destination;
    }
    public String getOfferid() {
        return offerid;
    }
    public String getRegistration() {
        return registration;
    }
    public List<String> getRiderIds() {
        return riderIds;
    }
    public int getSeat() {
        return seats;
    }
    public int getSeats() {
        return seats;
    }
    public nLatLong getSource() {
        return source;
    }

    public String getUserName() {
        return userName;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    public void setDestination(nLatLong destination) {
        this.destination = destination;
    }
    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    public void setRiderIds(List<String> riderIds) {
        this.riderIds = riderIds;
    }
    public void setSeat(int seat) {
        this.seats = seat;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public void setSource(nLatLong source) {
        this.source = source;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }


}

