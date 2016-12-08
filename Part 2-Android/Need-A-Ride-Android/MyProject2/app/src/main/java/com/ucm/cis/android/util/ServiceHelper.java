package com.ucm.cis.android.util;

import android.content.Context;

import com.ucm.cis.android.db.nOffer;
import com.ucm.cis.android.db.nRequest;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;

/**
 * Created by LekshmiPriya on 4/27/2016.
 */
public class ServiceHelper {


    private static final String BASE_URL = "http://192.168.1.78:8181/needaride/";
    private static final String ADD_OFFER = BASE_URL+"offer?userName=";
    private static final String ADD_REQUEST = BASE_URL+"request?userName=";
    private static final String HITCH = BASE_URL+"search?";
    //private static final String LOGIN = "http://192.168.102.1:8181/needaride/login?userName=leks&password=pass";
    //private static final String LOGIN = "http://denverpost.com/sports";
    //private static final String LOGIN = "https://api.github.com/users/mralexgray/repos";
    private static final String LOGIN = BASE_URL+"login?userName=leks&password=pass";
    private static final String ACCEPT = BASE_URL+"accept?offerid=5718e7f60a6389d88b7088c8&riderid=kkk&requestid=57006fd601637d7a8c09e9bf";
    private static final String PROFILE = BASE_URL+"myProfile?userName=leks";
    private static final String SELECT_OFFER = BASE_URL+"/select?userName=leks&id=";
    private static final String DELETE_OFFER = BASE_URL+"/delete?id=";
    HttpGet httpGet;
    RideServiceClient task;
    private Context ctx;

    public ServiceHelper(Context ctx){
        this.ctx = ctx;
    }
    public void getProfile(){
        try {
            httpGet = new HttpGet(new URI(PROFILE));
            task = new RideServiceClient(ctx, "Login");
            task.execute(httpGet);

        }catch (Exception e) {
            System.out.println( e.getMessage());

        }
    }

    public boolean selectRide(String id, int d){
        try {
             httpGet = new HttpGet(new URI(SELECT_OFFER+id));
            task = new RideServiceClient(ctx, "Select");
            task.execute(httpGet);
            return true;
        }catch (Exception e) {
            System.out.println( e.getMessage());
             return false;
        }
    }
    public boolean deleteRide(String id, int d){
        try {
            httpGet = new HttpGet(new URI(DELETE_OFFER+id));
            task = new RideServiceClient(ctx, "Delete");
            task.execute(httpGet);
            return true;
        }catch (Exception e) {
            System.out.println( e.getMessage());
            return false;
        }
    }

    public boolean addOffer(nOffer newOffer) {
        try {
            httpGet = new HttpGet(new URI(ADD_OFFER+newOffer.getUserName().trim()+"&source="+
            newOffer.getSource().getLatitude().trim()+","+newOffer.getSource().getLongitude().trim()+
                    "&sAddress="+newOffer.getSource().getAddress()+
            "&destination="+newOffer.getDestination().getLatitude().trim()+","+newOffer.getDestination().getLongitude().trim()+
                    "&dAddress="+newOffer.getDestination().getAddress()+
                    "&datetime="+newOffer.getDattim().trim()+"&seats="+newOffer.getSeats()));
            task = new RideServiceClient(ctx, "AddOffer");
            task.execute(httpGet);
            return true;
        }catch (Exception e) {
            System.out.println( e.getMessage());
            return false;
        }

    }
    public boolean addRequest(nRequest request) {
        try {
            httpGet = new HttpGet(new URI(ADD_REQUEST+request.getUserName().trim()+"&source="+
                    request.getSource().getLatitude().trim()+","+request.getSource().getLongitude().trim()+
                    "&sAddress="+request.getSource().getAddress()+
                    "&destination="+request.getDestination().getLatitude().trim()+","+request.getDestination().getLongitude().trim()+
                    "&dAddress="+request.getDestination().getAddress()+
                    "&datetime="+request.getDattim().trim()));
            task = new RideServiceClient(ctx, "AddRequest");
            task.execute(httpGet);
            return true;
        }catch (Exception e) {
            System.out.println( e.getMessage());
            return false;
        }

    }
    public boolean hitchRides(String url) {
        try {
            httpGet = new HttpGet(new URI(HITCH+url));
            task = new RideServiceClient(ctx, "Search");
            task.execute(httpGet);
            return true;
        }catch (Exception e) {
            System.out.println( e.getMessage());
            return false;
        }

    }
}
