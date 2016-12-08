package com.ucm.cis.android.myproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ucm.cis.android.db.DatabaseHelper;
import com.ucm.cis.android.db.Offer;
import com.ucm.cis.android.db.Request;
import com.ucm.cis.android.db.nLatLong;
import com.ucm.cis.android.db.nOffer;
import com.ucm.cis.android.util.ServiceHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class OfferRideActivity extends ActionBarActivity implements View.OnClickListener, LocationListener {
    Toolbar toolbar;
    Context context;
    Button offerBtn;
    nOffer newOffer;
    DatabaseHelper db;
    Calendar c = new GregorianCalendar();
    SimpleDateFormat df3, df4, df5, df6, df7;
    int m, da, y, h, mi;
    EditText s, d, date, time, seat;//, regn, username;
    private String lati, longi;
    private String myAddress, provider, myLocale;
    private LocationManager locationManager;
    Double latitude, longitude;
    private Location location;
    private Geocoder geocoder;
    ServiceHelper helper;//= new ServiceHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        context = OfferRideActivity.this;
        SQLiteDatabase.CursorFactory factory = null;
        db = new DatabaseHelper(this, factory);
        helper = new ServiceHelper(OfferRideActivity.this);

        offerBtn = (Button) findViewById(R.id.offerRide);
        offerBtn.setOnClickListener(this);
        s = (EditText) findViewById(R.id.source);
        d = (EditText) findViewById(R.id.dest);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        seat = (EditText) findViewById(R.id.seats);
      //  regn = (EditText) findViewById(R.id.registration);
        //username = (EditText) findViewById(R.id.userName);

        //Find Location and Set Current adress in Text Box

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        if (null != locationManager) {
            Criteria criteria = new Criteria();
            try {
                provider = locationManager.getBestProvider(criteria, false);
                location = locationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                System.out.println("Error occured geting Last location");
            }
        }
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
            s.setText(myLocale);
        } else {
            location = new Location("");
            location.setLatitude(39.0256);
            location.setLongitude(-94.6561);
            s.setText("Mission");
        }


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
        da = Integer.parseInt(df4.format(c.getTime()));
        y = Integer.parseInt(df5.format(c.getTime()));
        h = Integer.parseInt(df6.format(c.getTime()));
        mi = Integer.parseInt(df7.format(c.getTime()));


        TimeZone timeZone = TimeZone.getDefault();
        df2.setTimeZone(TimeZone.getTimeZone(("GMT-5:00")));
        date.setText(df1.format(c.getTime()));
        time.setText(df2.format(c.getTime()));
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_offer_ride, menu);
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
    public void onClick(View v) {
        if (v.getId() == R.id.offerRide) {
            try {
                newOffer = new nOffer();
                if (null != s.getText() && s.getText().length() != 0 &&
                        null != d.getText() && d.getText().length() != 0 &&
                        null != date.getText() && date.getText().length() != 0 &&
                        null != time.getText() && time.getText().length() != 0 &&
                        null != seat.getText() && seat.getText().length() != 0
                        ) {

                    getLatLong(s.getText().toString());
                    String position = latitude + "," + longitude;
                    nLatLong abc = new nLatLong(position, s.getText().toString());
                    newOffer.setSource(abc);

                    getLatLong(d.getText().toString());
                    String position2 = latitude + "," + longitude;
                    nLatLong xyz = new nLatLong(position2, d.getText().toString());
                    newOffer.setDestination(xyz);
                    newOffer.setDattim(date.getText().toString().trim()+"/"+time.getText().toString().trim());
                    newOffer.setSeats(Integer.parseInt(seat.getText().toString().trim()));
                    newOffer.setUserName(getUserName());
                    boolean ret = helper.addOffer(newOffer);

                    if (ret) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                this);

                        // set title
                        alertDialogBuilder.setTitle("New Ride Offer");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("New Ride Offer Added. Click Ok to continue")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(OfferRideActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        OfferRideActivity.this.finish();
                                        sendNotificationToRequestors(newOffer);
                                    }
                                });


                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error occured when adding an offer", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter All Values", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.date) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(OfferRideActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            }, y, m - 1, da);
            datePickerDialog.show();
        } else if (v.getId() == R.id.time) {
            TimePickerDialog timepicker = new TimePickerDialog(OfferRideActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    private void sendNotificationToRequestors(nOffer newOffer) {
        NotificationStarter dbGet = new NotificationStarter(context);
        //  dbGet.execute(newOffer);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = (location.getLatitude());
        longitude = (location.getLongitude());
        myAddress = getAddress(latitude, longitude);
    }


    private void getLatLong(String addr) throws IOException {

        List<Address> addressList = geocoder.getFromLocationName(addr, 1);
        Address address = addressList.get(0);

        if (address.hasLatitude() && address.hasLongitude()) {
            latitude = address.getLatitude();
            longitude = address.getLongitude();
//            System.out.println("selectedLat: " + latitude + " & selectedLng: " + longitude);
        }
    }

    private String getAddress(double lat, double lon) {
        geocoder = new Geocoder(this);
        List<Address> addresses = null;
        myAddress = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            myLocale = addresses.get(0).getLocality().toString() + ",";
            myLocale = myLocale + addresses.get(0).getAdminArea();
            myAddress = addresses.get(0).getAddressLine(0) + ",";
            myAddress = myAddress + addresses.get(0).getAddressLine(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myAddress;
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

    private class NotificationStarter extends AsyncTask<Offer, Object, ArrayList<Request>> {
        Context mcontext;
        DatabaseHelper db;
        Offer offer;

        public NotificationStarter(Context context) {
            this.mcontext = context;
        }

        //        Integer progress = new Integer(0);
        @Override
        protected ArrayList<Request> doInBackground(Offer... params) {
            SQLiteDatabase.CursorFactory factory = null;
            db = new DatabaseHelper(mcontext, factory);
            offer = (Offer) params[0];
            ArrayList<Request> requests = db.getAllMatchingRequests(" where source='" + offer.getSource() + "' and destination='" + offer.getDestination() + "'");
            return requests;
        }

        @Override
        protected void onPostExecute(ArrayList<Request> requests) {
            for (Request request : requests) {
                //send the request obj and offer obj for notification class
                System.out.println(" Hello " + offer.getSource());
                sendNotification(offer);
            }
        }

        private void sendNotification(Offer offer) {

            final int ATTENTION_ID = 1;
            Log.i("TimedService", "in sendNotification");
            String strNS = Context.NOTIFICATION_SERVICE;
            NotificationManager nManager = (NotificationManager) getSystemService(strNS);

            Context context = OfferRideActivity.this;
            int icon = R.drawable.green_button;
            long when = System.currentTimeMillis();

            CharSequence tickerText = "You Got A Ride!!!";
            CharSequence contentTitle = "Ride you asked for is available";
            CharSequence contentText = "Go and Accept it!!!";

            Intent nIntent = new Intent(context, RideDetailsActivity.class);

            nIntent.putExtra("ride", offer);
            System.out.println(" Ride ##" + offer.getSource());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            Notification notification = new Notification(icon, tickerText, when);
           // notification.setLatestEventInfo(context, contentTitle, contentText, pendingIntent);
            notification.flags = notification.FLAG_AUTO_CANCEL;
//notification.vibrate = new Long[1000,1000,1000,1000,1000];
            nManager.notify(ATTENTION_ID, notification);
        }
    }
}
