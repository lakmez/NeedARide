package com.ucm.cis.android.db;

import java.io.Serializable;

/**
 * Created by LekshmiPriya on 4/30/2016.
 */
public class nLatLong implements Serializable {

    private String latitude;
    private String longitude;
    private String address;

    public nLatLong(String position, String address) {
        // TODO Auto-generated constructor stub
        String[] pos = position.split(",");
        this.latitude = pos[0];
        this.longitude = pos[1];
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public nLatLong() {
        // TODO Auto-generated constructor stub
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



}
