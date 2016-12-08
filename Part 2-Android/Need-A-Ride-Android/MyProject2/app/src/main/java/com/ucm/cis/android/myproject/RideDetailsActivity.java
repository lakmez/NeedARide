package com.ucm.cis.android.myproject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ucm.cis.android.db.DatabaseHelper;
import com.ucm.cis.android.db.nOffer;
import com.ucm.cis.android.db.nRequest;
import com.ucm.cis.android.util.RideServiceClient;
import com.ucm.cis.android.util.ServiceHelper;

import java.text.SimpleDateFormat;


public class RideDetailsActivity extends ActionBarActivity implements View.OnClickListener {
    Toolbar toolbar;
    nOffer ofr;
    nRequest request;
    TextView so, ds, dt, tm, sts;
    Spinner spinner;
    String src, dest, date, time;
    int seats;
    boolean active;
    DatabaseHelper db;
    Button join, delete;
    String rideUserName;
    ServiceHelper helper = new ServiceHelper(RideDetailsActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        join = (Button) findViewById(R.id.join_button);
        delete = (Button) findViewById(R.id.deleteBtn);
        join.setOnClickListener(this);
        delete.setOnClickListener(this);
        //  SQLiteDatabase.CursorFactory factory = null;

        Intent offerintent = getIntent();
        Bundle etx = offerintent.getExtras();
        ofr = (nOffer) etx.getSerializable("ride");
        request = (nRequest) etx.getSerializable("req");
        if (null != ofr) {
            src = ofr.getSource().getAddress();
            dest = ofr.getDestination().getAddress();
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(ofr.getDatetime());
            //time = ofr.getTime();
            seats = ofr.getSeats();
            rideUserName = ofr.getUserName();
        }

        if (null != request) {
            src = request.getSource().getAddress();
            dest = request.getDestination().getAddress();
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(request.getDatetime());
            //time = ofr.getTime();
            active = request.isActive() ? true : false;
            rideUserName = request.getUserName();
        }
        //int seats = 5;
        spinner = (Spinner) findViewById(R.id.seatsspinner);
        TextView spinnerlbl = (TextView) findViewById(R.id.spinnerlbl);
        so = (TextView) findViewById(R.id.src1);
        ds = (TextView) findViewById(R.id.dst1);
        dt = (TextView) findViewById(R.id.dat1);
        tm = (TextView) findViewById(R.id.tme1);
        sts = (TextView) findViewById(R.id.seat1);
        //sts = (TextView) findViewById(R.id.seat1);

        so.setText("From:" + src);
        ds.setText("To:" + dest);
        dt.setText(date);
        if (null != ofr) {
            sts.setText("Seats:" + String.valueOf(seats));
        } else {
            sts.setText("");
            spinnerlbl.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            join.setVisibility(View.INVISIBLE);
            sts.setText("Active:" + active);
        }


        if (null != ofr) {
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);

            for (int k = 1; k <= seats; k++) {
                String se = String.valueOf(k);
                spinnerAdapter.add(se);

            }
            spinnerAdapter.notifyDataSetChanged();

        }
        delete.setVisibility(View.INVISIBLE);
        spinner.setEnabled(false);
        // delete.setEnabled(false);
        // String userName = getUserName();
        if (rideUserName.equals(getUserName())) {
           // delete.setEnabled(true);
            join.setVisibility(View.INVISIBLE);
            //delete.setVisibility(View.VISIBLE);
        }
    }

    private String getUserName() {
        SharedPreferences prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);
        String storedUsername = prefs.getString("username", "");
        return storedUsername;
    }

   /* private String getUserName() {
        SharedPreferences prefs = getSharedPreferences("Rider", Context.MODE_PRIVATE);
        String storedUsername = prefs.getString("username", "");
        return storedUsername;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ride_details, menu);
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


    private BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT);
            String response = intent.getStringExtra(RideServiceClient.HTTP_RESPONSE);
            //t.setText(response);
            Log.i("Profile", "RESPONSE = " + response);
            //parse(response);
        }
    };


    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("Select");
        registerReceiver(receiver1, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver1);
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.join_button) {
            String sel = (String) spinner.getSelectedItem();
            int newS = seats;
            if (null != sel) {
                newS = seats - Integer.parseInt(sel);
            }
            //double d =
            boolean res = helper.selectRide(ofr.getOfferid(), newS);
            //db.registerRide(ofr.getOfferid(), newS);
            if (res) {
                // Toast.makeText(getApplicationContext(), "Pleas" + newS, Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                // set title
                alertDialogBuilder.setTitle("New Ride Offer");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Successfully took this offer. Click Ok to continue")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity and go back to Main

                                Intent intent = new Intent(RideDetailsActivity.this, MainActivity.class);
                                startActivity(intent);
                                RideDetailsActivity.this.finish();
                            }
                        });
                           /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });*/

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            } else {
                Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
