package com.ucm.cis.android.myproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ucm.cis.android.db.DatabaseHelper;
import com.ucm.cis.android.db.Offer;

import java.util.ArrayList;


public class BrowseRideActivity extends ActionBarActivity implements LocationListener {
    Toolbar toolbar;
    private LocationManager locationManager;
    private String cityName, provider;
    private Location location;
    private double latitude, longitude;
    private DatabaseHelper db;
    private Offer[] offerArray;
    private CustomAdapter adapter;
    private ListView nearestLV;
    private ArrayList<Offer> nearestOffers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_ride);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SQLiteDatabase.CursorFactory factory = null;
        db = new DatabaseHelper(this, factory);
        update();
/*//*/
        //get Current adress and show in text box
        // Get the location manager
        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        if (null != locationManager) {
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            latitude = (location.getLatitude());
            longitude = (location.getLongitude());
        }
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }else{
            location= new Location("");
            location.setLatitude(39.0256);
            location.setLongitude(-94.6561);
        }

        offerArray = getNearestRide();
        nearestLV = (ListView) findViewById(R.id.nearestList);
        adapter = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
        nearestLV.setAdapter(adapter);


        nearestLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer selOffer = nearestOffers.get(position);
                Intent inter = new Intent(BrowseRideActivity.this, RideDetailsActivity.class);
                inter.putExtra("ride", selOffer);
                BrowseRideActivity.this.startActivity(inter);
            }
        });*/
    }

    private Offer[] getNearestRide() {//should call service and get nearest rides to offerarray
        ArrayList<Offer> allOffers = new ArrayList<>();
        nearestOffers = new ArrayList<>();
        //getting all offers
        allOffers = db.getAllOffers(" where seats>0");
        for (Offer offer : allOffers) {

            double lat2 = Double.parseDouble(offer.getSlatLong().getLatitude());
            double long2 = Double.parseDouble(offer.getSlatLong().getLongitude());
            float distanceInMeters = (float) getDistance(latitude, longitude, lat2, long2);
            System.out.println(" distance --->" + distanceInMeters);
            if (distanceInMeters < 5) {
                double mi = distanceInMeters * 1;//0.000621371192;
                offer.setDistance((float) mi);
                nearestOffers.add(offer);
            }
        }
        offerArray = new Offer[nearestOffers.size()];
        nearestOffers.toArray(offerArray);
        return offerArray;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }

    public void update() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        if (null != locationManager) {
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);


            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Location location = null;
            try {
                location = locationManager.getLastKnownLocation(provider);
                latitude = (location.getLatitude());
                longitude = (location.getLongitude());
                onLocationChanged(location);
            } catch (SecurityException e) {
                System.out.println("Error occured geting Last location");
            }
        }
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            location= new Location("");
            location.setLatitude(39.0256);
            location.setLongitude(-94.6561);
        }

        offerArray = getNearestRide();
        nearestLV = (ListView) findViewById(R.id.nearestList);
        adapter = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
        nearestLV.setAdapter(adapter);


        nearestLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer selOffer = nearestOffers.get(position);
                Intent inter = new Intent(BrowseRideActivity.this, RideDetailsActivity.class);
                inter.putExtra("ride", selOffer);
                BrowseRideActivity.this.startActivity(inter);
            }
        });
    }

    private double getDistance(double lat1, double long1, double lat2, double long2) {

        System.out.println("latitudes--->" + lat1 + " " + lat2 + " /nlongi-" + long1 + " " + long2);
        System.out.println("my cordinates -->" + lat1 + " " + long1);
        double distance = getDistance2(lat1, long1, lat2, long2);
        System.out.println(" distance double--->" + distance);
        return distance;
    }

    //Haversines formula
    public double getDistance2(double lat1, double lon1, double lat2, double lon2) {
       /* int R = 6371; // km
        double x = (lon2 - lon1) * Math.cos((lat1 + lat2) / 2);
        double y = (lat2 - lat1);
        double distance = Math.sqrt(x * x + y * y) * R;
        return distance;*/
        int EARTH_RADIUS_KM = 6371;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLonRad = Math.toRadians(lon2 - lon1);

        return Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad)) * EARTH_RADIUS_KM;
    }

    public static float getDistance1(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_ride, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("location changed ------");
        //   Toast.makeText(getApplicationContext(),"Location changed00", Toast.LENGTH_LONG).show();
        latitude = (location.getLatitude());
        longitude = (location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
