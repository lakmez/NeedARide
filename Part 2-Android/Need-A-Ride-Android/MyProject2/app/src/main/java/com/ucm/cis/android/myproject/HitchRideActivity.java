package com.ucm.cis.android.myproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.ucm.cis.android.db.LatLong;
import com.ucm.cis.android.db.Offer;
import com.ucm.cis.android.db.nOffer;
import com.ucm.cis.android.db.parseObjects.TestObjects;
import com.ucm.cis.android.util.RideServiceClient;
import com.ucm.cis.android.util.ServiceHelper;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class HitchRideActivity extends ActionBarActivity implements View.OnClickListener {
    Toolbar toolbar;
    Button b;
    EditText src, dest, date, time;
    ListView searchListView;
    String source, destination, date1, time1;
    // DatabaseHelper db;
    ServiceHelper helper = new ServiceHelper(HitchRideActivity.this);
    ArrayList<nOffer> nOffer;
    nOffer[] offerArray;
    CustomAdapter adapter;
    Calendar c;
    SimpleDateFormat df3, df4, df5, df6, df7;
    int m, d, y, h, mi;
    private ArrayList<Offer> nearestOffers;
    private double latitude, longitude;
    private float distanceSource;
    private Geocoder geocoder;
    private float distanceDest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitch_ride);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        b = (Button) findViewById(R.id.search_button);
        src = (EditText) findViewById(R.id.src);
        dest = (EditText) findViewById(R.id.dst);
        date = (EditText) findViewById(R.id.dat);
        time = (EditText) findViewById(R.id.tme);

        c = Calendar.getInstance();
        java.text.SimpleDateFormat df1 = new java.text.SimpleDateFormat("MM-dd-yyyy");
        java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("HH:mm");
        TimeZone timeZone = TimeZone.getDefault();
        df2.setTimeZone(TimeZone.getTimeZone(("GMT-5:00")));
        //  date.setText(df1.format(c.getTime()));
        // time.setText(df2.format(c.getTime()));
        date.setOnClickListener(this);
        time.setOnClickListener(this);
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

        searchListView = (ListView) findViewById(R.id.searchListRides);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nOffer selOffer = nOffer.get(position);
                Intent inter = new Intent(HitchRideActivity.this, RideDetailsActivity.class);
                inter.putExtra("ride", selOffer);
                HitchRideActivity.this.startActivity(inter);
            }
        });
        b.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hitch_ride, menu);
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
        if (v.getId() == R.id.search_button) {
          /*  nOffer = new ArrayList<>();
            offerArray = new Offer[nOffer.size()];
            nOffer.toArray(offerArray);
            adapter = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
            adapter.clear();
            adapter.notifyDataSetChanged();*/
            source = src.getText().toString().trim();
            destination = dest.getText().toString().trim();
            if (null != source && null != destination && !source.isEmpty() && !destination.isEmpty()) {
                date1 = date.getText().toString().trim();
                time1 = time.getText().toString().trim();
                if (null != source && null != destination) {
                    nOffer = new ArrayList<>();
                    findMatchingOffer(source, destination, date1, time1);
                } else {
                    Toast.makeText(this, "Please Enter Source And Destination Values", Toast.LENGTH_LONG).show();
                }

//        Toast.makeText(getApplicationContext(), "Ride found contact-" + soffer.getUserName(), Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(getApplicationContext(), "Please Enter Source and Destination", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.dat) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(HitchRideActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        } else if (v.getId() == R.id.tme) {
            TimePickerDialog timepicker = new TimePickerDialog(HitchRideActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                    /*if (hourOfDay < 10) {
                        hh = "0" + hh;
                    }/*/
                    String mm = String.valueOf(minute);
                   /* if (minute < 10) {
                        mm = "0" + mm;
                    }*/
                    String t = hh + ":" + mm;
                    time.setText(t);
                }
            }, h, mi, false);
            timepicker.show();
        }
    }


    public void setupData(ArrayList offerList) {
        nOffer = offerList;
        if (null != nOffer && nOffer.size() != 0) {

            offerArray = new nOffer[nOffer.size()];
            nOffer.toArray(offerArray);
            adapter = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
            searchListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            nOffer = new ArrayList<>();
            offerArray = new nOffer[nOffer.size()];
            nOffer.toArray(offerArray);
            adapter = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
            adapter.clear();
            adapter.notifyDataSetChanged();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle("No Matching Offers Found");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Do you want to Request A Ride for this route?" +
                            "Click Cancel to stay here and search again")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(HitchRideActivity.this, RequestRideActivity.class);
                            startActivity(intent);
                            HitchRideActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
    }

    private void findMatchingOffer(String source, String destination, String date1, String time1) {
        //find destns with longi and lati within 1 miles away from source and dest
       // ArrayList<Offer> matchingOffers = new ArrayList<>();
        //ArrayList<Offer> allOffers = new ArrayList<>();
        //   allOffers = db.getAllOffers("");
        //for (Offer ofr : allOffers)
        {
           // LatLong dlatlong = ofr.getDlatLong();
            //LatLong slatlng = ofr.getSlatLong();
          //  double sourceDist, destDist;
            try {
                LatLong mySLatLong = getLatLong(source);
                LatLong myDLatLong = getLatLong(destination);
                String url = "userName=" + getUserName() + "&source=" + mySLatLong.getLatitude() + "," + mySLatLong.getLongitude() +
                        "&sAddress=" + source +
                        "&destination=" + myDLatLong.getLatitude() + "," + myDLatLong.getLongitude() +
                        "&dAddress=" + destination +
                        "&datetime=" + date1 + "/" + time1;

                boolean ret = helper.hitchRides(url);
            } catch (Exception e) {


            }
        }
    }


    private BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT);
            String response = intent.getStringExtra(RideServiceClient.HTTP_RESPONSE);
            Log.i("Profile", "RESPONSE = " + response);
            parse(response);
        }
    };

    private void parse(String response) {
        try {
            response = response.replaceAll("content","offer");
            JSONArray jsonArray = new JSONArray(response);
            Type listType = new TypeToken<List<com.ucm.cis.android.db.nOffer>>() {
            }.getType();
            Type listType2 = new TypeToken<List<TestObjects>>() {
            }.getType();

            //
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });
            Gson gson = gsonBuilder.create();

            ArrayList testObjects = gson.fromJson(response, listType2);
            ArrayList offerList = parseTestObjects(testObjects);
            setupData(offerList);
            // requestList = gson.fromJson(jsonArray.get(1).toString(), listType2);
            /*offerArray = offerList.toArray(new nOffer[offerList.size()]);
            //rideArray = requestList.toArray(new nRequest[requestList.size()]);


            adapter1 = new CustomAdapter(this, R.layout.listview_item_row, offerArray);
            //adapter2 = new CustomAdapter(this, R.layout.listview_item_row, rideArray);
            offrList.setAdapter(adapter1);
            *///reqList.setAdapter(adapter2);

        } catch (Exception e) {

        }
    }

    private ArrayList parseTestObjects(ArrayList <TestObjects>testObjects) {
        ArrayList noffers = new ArrayList();
        for(TestObjects obj:testObjects){
            nOffer ofr = obj.getOffer();
            ofr.setDistance(obj.getDistance().getValue());
            noffers.add(ofr);
        }
        return noffers;
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("Search");
        registerReceiver(receiver1, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver1);
        super.onPause();
    }

    private String getUserName() {
        SharedPreferences prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);
        String storedUsername = prefs.getString("username", "");
        return storedUsername;
    }

   /* private double getDistance(double lat1, double long1, double lat2, double long2) {

        double distance = getDistance2(lat1, long1, lat2, long2);
        return distance;
    }

    public static float getDistance1(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

    public double getDistance2(double lat1, double lon1, double lat2, double lon2) {
        int EARTH_RADIUS_KM = 6371;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLonRad = Math.toRadians(lon2 - lon1);

        return Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad)) * EARTH_RADIUS_KM;
    }
*/
    private LatLong getLatLong(String addr) throws IOException {
        geocoder = new Geocoder(this);
        List<Address> addressList = geocoder.getFromLocationName(addr, 1);
        Address address = addressList.get(0);

        if (address.hasLatitude() && address.hasLongitude()) {
            latitude = address.getLatitude();
            longitude = address.getLongitude();
            System.out.println("selectedLat: " + latitude + " & selectedLng: " + longitude);
        }
        LatLong latlong = new LatLong(latitude, longitude);
        return latlong;
    }
}
