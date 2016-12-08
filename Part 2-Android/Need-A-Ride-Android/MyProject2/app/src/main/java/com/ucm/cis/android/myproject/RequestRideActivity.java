package com.ucm.cis.android.myproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.ucm.cis.android.db.LatLong;
import com.ucm.cis.android.db.nLatLong;
import com.ucm.cis.android.db.nRequest;
import com.ucm.cis.android.util.ServiceHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class RequestRideActivity extends ActionBarActivity implements View.OnClickListener, LocationListener {
    Toolbar toolbar;
    // DatabaseHelper db;
    ServiceHelper helper = new ServiceHelper(RequestRideActivity.this);
    EditText source, destination, date, time;//, username;
    nRequest request;
    Button req_btn;
    Calendar c;
    SimpleDateFormat df3, df4, df5, df6, df7;
    int m, d, y, h, mi;
    private LocationManager locationManager;
    String provider, cityName, myAddress, myLocale;
    private Location location;
    private double latitude;
    private double longitude;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ride);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SQLiteDatabase.CursorFactory factory = null;
      //  db = new DatabaseHelper(this, factory);

        source = (EditText) findViewById(R.id.source1);
        destination = (EditText) findViewById(R.id.destination1);
        date = (EditText) findViewById(R.id.date1);
        time = (EditText) findViewById(R.id.time1);
       // username = (EditText) findViewById(R.id.userName1);
        req_btn = (Button) findViewById(R.id.requestRide);
        req_btn.setOnClickListener(this);

        /*****Find Location and Set City name********/

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        if (null != locationManager) {
            Criteria criteria = new Criteria();
            try {
                provider = locationManager.getBestProvider(criteria, false);
                location = locationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                System.out.println("Error getting last known position");
            }
        }
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
            source.setText(cityName);
        } else {
            location = new Location("");
            location.setLatitude(39.0256);
            location.setLongitude(-94.6561);
            source.setText("Mission");
        }
/****************************end***************************/


        c = Calendar.getInstance();
        java.text.SimpleDateFormat df1 = new java.text.SimpleDateFormat("MM-dd-yyyy");
        java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("HH:mm");
        df3 = new java.text.SimpleDateFormat("M");
        df4 = new java.text.SimpleDateFormat("d");
        df5 = new java.text.SimpleDateFormat("yyyy");
        df6 = new java.text.SimpleDateFormat("hh");
        df7 = new java.text.SimpleDateFormat("mm");

        df6.setTimeZone(TimeZone.getTimeZone(("GMT-5:00")));
        df7.setTimeZone(TimeZone.getTimeZone(("GMT-5:00")));
        m = Integer.parseInt(df3.format(c.getTime()));
        d = Integer.parseInt(df4.format(c.getTime()));
        y = Integer.parseInt(df5.format(c.getTime()));
        h = Integer.parseInt(df6.format(c.getTime()));
        mi = Integer.parseInt(df7.format(c.getTime()));
        System.out.println("Dates " + df3.format(c.getTime()) + df4.format(c.getTime()) + df5.format(c.getTime()) + df6.format(c.getTime()) + df7.format(c.getTime()));
        TimeZone timeZone = TimeZone.getDefault();
        df2.setTimeZone(TimeZone.getTimeZone(("GMT-5:00")));
        date.setText(df1.format(c.getTime()));
        time.setText(df2.format(c.getTime()));
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request_ride, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.requestRide) {
            request = new nRequest();
            try {
                LatLong dLatLong = getLatLong(destination.getText().toString());
                LatLong sLatLong = getLatLong(source.getText().toString());

                request.setSource(new nLatLong(sLatLong.getLatitude() + "," + sLatLong.getLongitude()
                        , source.getText().toString().trim()));
                request.setDestination(new nLatLong(dLatLong.getLatitude() + "," + dLatLong.getLongitude()
                        , destination.getText().toString().trim()));


                request.setDattim(date.getText().toString().trim() + "/" +
                        time.getText().toString().trim());

                request.setUserName(getUserName());
                request.setActive(true);
            } catch (Exception e) {
            }
            boolean ret = helper.addRequest(request);

            if (ret) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                alertDialogBuilder.setTitle("Ride Request Added");
                alertDialogBuilder
                        .setMessage("Registered New Ride Request. You will receive notifications when a ride becomes available.\n Click Ok to continue")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(RequestRideActivity.this, MainActivity.class);
                                startActivity(intent);
                                RequestRideActivity.this.finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        } else if (v.getId() == R.id.date1) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(RequestRideActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    int mon = monthOfYear + 1;
                    String month = String.valueOf(mon);
                    String daq = String.valueOf(dayOfMonth);
                    if (mon < 10) {
                        month = "0" + month;
                    }
                    //int dd = monthOfYear+1;
                    if (dayOfMonth < 10) {
                        daq = "0" + daq;
                    }
                    String d2 = year + "-" + month + "-" + daq;
                    date.setText(d2);
                }
            }, y, m - 1, d);
            datePickerDialog.show();
        } else if (v.getId() == R.id.time1) {
            TimePickerDialog timepicker = new TimePickerDialog(RequestRideActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar datetime = Calendar.getInstance();
                    datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    datetime.set(Calendar.MINUTE, minute);
                    String am_pm = "AM";
                    if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "AM";
                    else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "PM";
                    String hh = String.valueOf(hourOfDay);
                    if (hourOfDay < 10) {
                        hh = "0" + hh;
                    }

                    String mm = String.valueOf(minute);
                    if (minute < 10) {
                        mm = "0" + mm;
                    }
                    String t = hh + ":" + mm;
                    time.setText(t);
                }
            }, h, mi, false);
            timepicker.show();
        }
    }

    private String getUserName() {
        SharedPreferences prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);
        String storedUsername = prefs.getString("username", "");
        return storedUsername;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = (location.getLatitude());
        longitude = (location.getLongitude());
        cityName = getAddress(latitude, longitude);
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


    private LatLong getLatLong(String addr) throws IOException {
        double la, lo;
        List<Address> addressList = geocoder.getFromLocationName(addr, 1);
        Address address = addressList.get(0);
        LatLong latLong = null;
        if (address.hasLatitude() && address.hasLongitude()) {
            la = address.getLatitude();
            lo = address.getLongitude();
            System.out.println("selectedLat: " + latitude + " & selectedLng: " + longitude);
            latLong = new LatLong(la, lo);
        }
        return latLong;
    }

    private String getAddress(double lat, double lon) {
        // Geocoder geocoder;
        geocoder = new Geocoder(this);
        List<Address> addresses = null;
        myAddress = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            myLocale = addresses.get(0).getLocality().toString() + ",";
            myLocale = myLocale + addresses.get(0).getAdminArea();
            // myAddress = addresses.get(0).getAddressLine(0) + ", ";
            // myAddress = myAddress + addresses.get(0).getAddressLine(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myLocale;
    }

}
