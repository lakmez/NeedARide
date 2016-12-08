package com.ucm.cis.android.db;

import java.io.Serializable;

public class LatLong implements Serializable {
    String latitude;
    String longitude;

    public LatLong(double lat, double lon) {
        this.latitude = String.valueOf(lat);
        this.longitude = String.valueOf(lon);
    }

    public LatLong(String lat, String lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getLatitude() {
        return latitude;
    }

    public double getLatitude1()
    {
         return Double.parseDouble(latitude);
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Double getLongitude1() {
        return Double.parseDouble(longitude);
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
